package com.example.trackingorder.service.implementService;

import com.example.trackingorder.configMapper.*;
import com.example.trackingorder.dto.request.fillter.OrderFillter;
import com.example.trackingorder.dto.request.order.*;
import com.example.trackingorder.dto.response.coupons.CouponsResponse;
import com.example.trackingorder.dto.response.order.*;
import com.example.trackingorder.entity.cartAndOrder.*;
import com.example.trackingorder.entity.payment.PaymentMethods;
import com.example.trackingorder.entity.products.Inventory;
import com.example.trackingorder.entity.products.ProductVariant;
import com.example.trackingorder.entity.tracking.TrackingLogs;
import com.example.trackingorder.entity.users.DeliveryAddress;
import com.example.trackingorder.entity.users.Users;
import com.example.trackingorder.exception.ResponseStatusException;
import com.example.trackingorder.repository.*;
import com.example.trackingorder.service.InterfaceService.CouponsService;
import com.example.trackingorder.service.InterfaceService.OrdersService;
import com.example.trackingorder.spec.OrderSpecification;
import com.example.trackingorder.status.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrdersImplement implements OrdersService {

    private final OrdersRepo ordersRepo;
    private final CouponsService couponsService;
    private final InventoryRepo inventoryRepo;
    private final CouponsRepo couponsRepo;
    private final ProductVariantRepo productVariantRepo;
    private final CartItemsRepo cartItemsRepo;
    private final UserRepo userRepo;
    private final DeliveryAddressRepo deliveryAddressRepo;
    private final PaymentMethodRepo paymentMethodRepo;
    private final OrderItemsRepo orderItemsRepo;
    private final TrackingLogRepo trackingLogRepo;
    private final OrderSummaryMapper orderSummaryMapper;
    private final PlaceOrderMapper placeOrderMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderBuyNowMapper orderBuyNowMapper;
    private final TrackingLogMapper trackingLogMapper;


    @Override
    public List<AllOrderResponse> getLatestOrders(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Orders> orders = ordersRepo.findAllByOrderByCreatedAtDesc(pageable);

        List<AllOrderResponse> responseList = new ArrayList<>();

        for (Orders order : orders.getContent()) {
            AllOrderResponse response = new AllOrderResponse();

            response.setOrderId(order.getId());
            response.setCustomerName(order.getUser().getFullName());
            response.setGrandTotal(order.getGrandTotal());
            response.setStatus(order.getOrderStatus());
            response.setCreatedAt(order.getCreatedAt());
//            if (order.getShipments() != null && order.getShipments().getCarrier() != null) {
//                response.setCarriersName(order.getShipments().().getName());
//            }
            responseList.add(response);
        }
        return responseList;

    }

    @Override
    public void bulkConfirm() {
        List<Orders> orders = ordersRepo.findByOrderStatus(OrderStatus.PENDING);

        for (Orders order : orders) {
            order.setOrderStatus(OrderStatus.SHIPPING);
        }
        ordersRepo.saveAll(orders);
    }

    @Override
    public RevenueResponse getRevenue() {
        BigDecimal revenue = ordersRepo.getTotalRevenueShipping();

        RevenueResponse response = new RevenueResponse();
        if (revenue != null) {
            response.setTotalRevenue(revenue);
        } else {
            response.setTotalRevenue(BigDecimal.ZERO);
        }
        return response;
    }

    @Override
    public OrderDetailResponse getOrderDetail(String userName, String orderId) {
        Orders order = ordersRepo.findByIdAndUser_UserName(orderId, userName)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItems> items = orderItemsRepo.findByOrder_Id(orderId);

        OrderDetailResponse response = orderMapper.toDto(order);

        List<OrderItem> itemResponses = new ArrayList<>();

        for (OrderItems item : items) {
            OrderItem dto = orderItemMapper.toDto(item);
            itemResponses.add(dto);
        }

        response.setItems(itemResponses);


        response.setSubtotal(order.getSubTotal());
        response.setDiscountAmount(order.getDiscountAmount());
        response.setShippingFee(order.getShippingFee());
        response.setGrandTotal(order.getGrandTotal());

        if (order.getShipments() != null && !order.getShipments().isEmpty()) {
            response.setTrackingNumber(order.getShipments().get(0).getTrackingNumber());
        }
        return response;
    }

    @Override
    public List<MyOrderResponse> getMyOrder(String userName, Integer pageSize, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Orders> orders = ordersRepo.findAllByUser_UserName(userName, pageable);

        List<MyOrderResponse> responseList = new ArrayList<>();

        for (Orders order : orders.getContent()) {
            MyOrderResponse response = new MyOrderResponse();

            response.setId(order.getId());
            if (!order.getShipments().isEmpty()) {
                response.setOrderCode(order.getShipments().getFirst().getTrackingNumber());
            }
            response.setOrderStatus(order.getOrderStatus());
            response.setGrandTotal(order.getGrandTotal());
            response.setCreatedAt(order.getCreatedAt());
            response.setPaymentMethod(order.getPaymentMethod().getName());

            int productCount = 0;

            for (OrderItems item : order.getOrderItems()) {
                productCount += item.getQuantity();
            }
            response.setProductCount(productCount);

            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public OrderStatisticsResponse getStatistics() {
        List<Object[]> result = ordersRepo.countOrdersByStatus();
        Map<OrderStatus, Long> orderStatusStatistics = new HashMap<>();
        long totalOrders = 0;

        for (Object[] row : result) {

            OrderStatus status = (OrderStatus) row[0];
            Long count = (Long) row[1];

            orderStatusStatistics.put(status, count);

            totalOrders += count;
        }

        OrderStatisticsResponse response = new OrderStatisticsResponse();

        response.setTotalOrders(totalOrders);
        response.setOrderStatusStatistics(orderStatusStatistics);

        return response;
    }

    @Override
    @Transactional
    public OrderStatusResponse confirmOrder(String id, String userName) {
        Users user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không timf thấy người dùng "));
        ;
        Orders order = ordersRepo.findOrderForCancel(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không timf thấy đơn hàng"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chỉ Xác nhận được đơn PENDING.");
        }

        Map<String, Inventory> inventoryMap = new HashMap<>();
        for (OrderItems item : order.getOrderItems()) {
            Inventory inventory = item.getProductVariant().getInventory();

            if (inventory.getQuantityInStock() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Sản phẩm không đủ tồn kho");
            }

            Long reservedQuantity = inventory.getReservedQuantity() - item.getQuantity();
            inventory.setReservedQuantity(reservedQuantity);

            if (inventory.getQuantityInStock() == 0) {
                inventory.setStatus(InventoryStatus.OUT_OF_STOCK);
            } else if (inventory.getQuantityInStock() < 5) {
                inventory.setStatus(InventoryStatus.LIMIDTED_STOCK);
            }
            inventoryMap.put(inventory.getId(), inventory);
        }
        inventoryRepo.saveAll(inventoryMap.values());

        OrderStatus previous = order.getOrderStatus();
        OrderStatus current = OrderStatus.CONFIRMED;

        order.setOrderStatus(current);
        ordersRepo.save(order);

        TrackingLogs log = trackingLogMapper.toTrackingLog(order, user, WorkflowStatus.CANCELLED, "ĐƠn hàng đã được xác nhận ", LocalDateTime.now());
        trackingLogRepo.save(log);

        OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
        orderStatusResponse.setOrderId(order.getId());
        orderStatusResponse.setPreviousStatus(previous);
        orderStatusResponse.setCurrentStatus(current);

        return orderStatusResponse;
    }

    @Override
    @Transactional
    public OrderStatusResponse cancelOrder(String id, String userName) {

        Users user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không timf thấy người dùng "));
        ;
        Orders order = ordersRepo.findOrderForCancel(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không timf thấy đơn hàng"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chỉ huỷ được đơn PENDING.");
        }

        OrderStatus previous = order.getOrderStatus();
        OrderStatus current = OrderStatus.CANCELLED;

        order.setOrderStatus(current);
        ordersRepo.save(order);

        Map<String, Inventory> inventoryMap = new HashMap<>();
        Inventory inventory = null;
        for (OrderItems item : order.getOrderItems()) {

            inventory = item.getProductVariant().getInventory();

            inventory.setQuantityInStock(inventory.getQuantityInStock() + item.getQuantity());

            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());

            inventoryMap.put(inventory.getId(), inventory);
        }
        inventoryRepo.saveAll(inventoryMap.values());

        TrackingLogs log = trackingLogMapper.toTrackingLog(order, user, WorkflowStatus.CANCELLED, "ĐƠn hàng đã bị hủy ", LocalDateTime.now());
        trackingLogRepo.save(log);

        OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
        orderStatusResponse.setOrderId(order.getId());
        orderStatusResponse.setPreviousStatus(previous);
        orderStatusResponse.setCurrentStatus(current);

        return orderStatusResponse;
    }

    @Override
    public List<OrderResponse> getOrderFillter(OrderFillter orderFillter, String userName, Integer pageSize, Integer pageNumber) {
        Specification<Orders> specification =
                Specification.where(OrderSpecification.equalUserName(userName));

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);


        if (orderFillter.getOrderId() != null && !orderFillter.getOrderId().isEmpty()) {
            specification = specification.and(OrderSpecification.likeOrderId(orderFillter.getOrderId()));
        }
        if (orderFillter.getOrderStatus() != null) {
            specification = specification.and(OrderSpecification.equalOrderStatus(orderFillter.getOrderStatus()));
        }
        if (orderFillter.getPaymentStatus() != null) {
            specification = specification.and(OrderSpecification.equalPaymentStatus(orderFillter.getPaymentStatus()));
        }
        if (orderFillter.getMinTotal() != null) {
            specification = specification.and(OrderSpecification.minTotal(orderFillter.getMinTotal()));
        }
        if (orderFillter.getMaxTotal() != null) {
            specification = specification.and(OrderSpecification.maxTotal(orderFillter.getMaxTotal()));
        }
        if (orderFillter.getToDate() != null) {
            specification = specification.and(OrderSpecification.toDate(orderFillter.getToDate()));
        }
        if (orderFillter.getFromDate() != null) {
            specification = specification.and(OrderSpecification.fromDate(orderFillter.getFromDate()));
        }

        Page<Orders> orderPage = ordersRepo.findAll(specification, pageable);

        List<OrderResponse> responseList = new ArrayList<>();

        for (Orders order : orderPage.getContent()) {
            OrderResponse response = new OrderResponse();

            response.setOrderId(order.getId());
            response.setUserName(order.getUser().getUserName());
            response.setGrandTotal(order.getGrandTotal());
            response.setOrderStatus(order.getOrderStatus());
            response.setPaymentStatus(order.getPaymentStatus());

            response.setProvince(order.getDeliveryAddress().getProvince());
            response.setStreet_detail(order.getDeliveryAddress().getStreetDetail());

            List<OrderItemsResponse> itemResponses = new ArrayList<>();
            for (OrderItems item : order.getOrderItems()) {

                OrderItemsResponse itemResponse = new OrderItemsResponse();

                itemResponse.setProductName(item.getProductVariant().getProduct().getName());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setProductPrice(item.getPrice());

                itemResponses.add(itemResponse);
            }

            response.setOrderItemsResponseList(itemResponses);

            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public List<OrderResponse> getOrder(String userName) {
        List<Orders> orders = ordersRepo.findALlByUserName(userName);
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NGười dùng chưa có đơn hàng nào ");
        }

        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Orders order : orders) {
            OrderResponse orderResponse = orderMapper.toOrderResponse(order);

            List<OrderItemsResponse> orderItemsResponseList = new ArrayList<>();

            List<OrderItems> orderItems = order.getOrderItems();
            for (OrderItems item : orderItems) {

                ProductVariant productVariant = item.getProductVariant();
                Inventory inventory = productVariant.getInventory();

                OrderItemsResponse itemResponse = orderItemMapper.toOrderItemsResponse(item);

                if (inventory.getQuantityInStock() < item.getQuantity()) {
                    itemResponse.setMessage("Tồn Kho Không đủ để xử lí đơn hàng , vui lonhg nhập lại ..... ");
                } else {
                    itemResponse.setMessage("");
                }

                orderItemsResponseList.add(itemResponse);
            }
            orderResponse.setOrderItemsResponseList(orderItemsResponseList);

            orderResponseList.add(orderResponse);

        }
        return orderResponseList;
    }

    @Override
    @Transactional
    public void byeNow(BuyNowRequest request, String userName) {

        ProductVariant productVariant = productVariantRepo.findById(request.getProductVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại"));

        Inventory inventory = inventoryRepo.findByProductVariantId(request.getProductVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tồn kho không có thông tin "));

        Users user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không thấy nguời dùng"));

        DeliveryAddress deliveryAddress = deliveryAddressRepo.findByIdAndUserId(request.getDeliveryAddressId(), user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Địa chỉ giao hàng không hợp lệ"));

        PaymentMethods paymentMethod = paymentMethodRepo.findByIdAndIsActive(request.getPaymentMethodId(), true)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phương thức thanh toán không hợp lệ"));

        Long available = inventory.getQuantityInStock() - inventory.getReservedQuantity();
        if (available < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm không đủ số lượng trong kho. Còn lại: " + available);
        }

        BigDecimal subtotal = productVariant.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        BigDecimal shippingFee = BigDecimal.valueOf(30000);

        BigDecimal discountAmount = BigDecimal.ZERO;
        Coupons coupon = null;

        if (request.getCouponCode() != null && !request.getCouponCode().isBlank()) {
            coupon = couponsRepo.findByCodeAndStatus(request.getCouponCode(), Status.ACTIVE)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mã giảm giá không hợp lệ hoặc đã hết hạn"));

            if (coupon.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mã giảm giá đã hết hạn");
            }

            if (coupon.getUseCount() >= coupon.getUseLimit()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mã giảm giá hết lượt sử dụng ");
            }

            if (coupon.getType() == Type.PERCENT) {
                discountAmount = subtotal.multiply(coupon.getValue()).divide(BigDecimal.valueOf(100), 2);
            } else {
                discountAmount = coupon.getValue();
            }

            // Tăng use_count
            coupon.setUseCount(coupon.getUseCount() + 1);
            couponsRepo.save(coupon);
        }

        BigDecimal grandTotal = subtotal.add(shippingFee).subtract(discountAmount);

        Orders order = orderBuyNowMapper.toOrder(user, deliveryAddress, paymentMethod, coupon,
                OrderStatus.PENDING, subtotal, discountAmount, shippingFee, grandTotal, PaymentStatus.UNPAID);
        ordersRepo.save(order);

        OrderItems orderItem = orderItemMapper.toOrderItem2(order, productVariant,
                request.getQuantity(), productVariant.getPrice());
        orderItemsRepo.save(orderItem);

        // TĂng reserved trong inventory lên vì đã được đặt
        inventory.setReservedQuantity(inventory.getReservedQuantity() + request.getQuantity());

        // Cập nhật tồn kho
        Long newQuantity = inventory.getQuantityInStock() - inventory.getReservedQuantity();
        if (newQuantity <= 0) {
            inventory.setStatus(InventoryStatus.OUT_OF_STOCK);
        } else if (newQuantity <= 5) {
            inventory.setStatus(InventoryStatus.LIMIDTED_STOCK);
        }
        inventoryRepo.save(inventory);

        // tạo tracking log cho đơn hàng này
        TrackingLogs trackingLog = trackingLogMapper.toTrackingLog(order, user, WorkflowStatus.PENDING,
                "Đơn hàng vừa được tạo qua Mua Ngay", LocalDateTime.now());
        trackingLogRepo.save(trackingLog);
    }

    @Override
    @Transactional
    public OrdersSummaryResponse getOrdersSummary(OrderSummaryRequest request, String userName) {
        if (request.getOrderSummaryItemRequests() == null || request.getOrderSummaryItemRequests().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Danh sách sản phẩm không được để trống");
        }

        List<String> productVariantIds = new ArrayList<>();

        for (OrderSummaryItemRequest item : request.getOrderSummaryItemRequests()) {
            productVariantIds.add(item.getProductId());
        }

        List<CartItems> cartItemsList = cartItemsRepo.findByProductVariantIdInAndUserName(productVariantIds, userName);

        if (cartItemsList.size() != productVariantIds.size()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Có sản phẩm không thuộc giỏ hàng");
        }

        Map<String, CartItems> cartItemMap = new HashMap<>();
        for (CartItems cartItem : cartItemsList) {
            cartItemMap.put(cartItem.getProductVariant().getId(), cartItem);
        }

        BigDecimal subTotal = BigDecimal.ZERO;

        for (OrderSummaryItemRequest item : request.getOrderSummaryItemRequests()) {
            CartItems cartItem = cartItemMap.get(item.getProductId());

            if (cartItem == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm không tồn tại");
            }

            if (!cartItem.getQuantity().equals(item.getQuantity())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Số lượng sản phẩm đã thay đổi, hãy nhập đúng");
            }
            subTotal = subTotal.add(cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        BigDecimal discountValue = BigDecimal.ZERO;
        CouponsResponse coupons = couponsService.validateCoupon(request.getPromotionCode());

        if (coupons.getCouponType() == Type.PERCENT) {
            discountValue = subTotal.multiply(coupons.getValue()).divide(BigDecimal.valueOf(100));
        } else {
            discountValue = coupons.getValue();
        }

        BigDecimal shippingFee = new BigDecimal("30000");
        BigDecimal totalAmount = subTotal.subtract(discountValue).add(shippingFee);

        OrdersSummaryResponse summaryResponse = orderSummaryMapper.toResponse(subTotal, discountValue, shippingFee, totalAmount);
        return summaryResponse;
    }

    @Override
    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderReq placeOrderReq, String userName) {
        Users user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy user"));

        DeliveryAddress address = deliveryAddressRepo.findByIdAndUserId(placeOrderReq.getDeliveryAddressId(), user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Địa chỉ không hợp lệ"));

        PaymentMethods paymentMethod = paymentMethodRepo.findByIdAndIsActive(placeOrderReq.getPaymentMethodId(), true)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phương thức thanh toán không hợp lệ"));

        List<String> productVariantIds = new ArrayList<>();
        for (OrderSummaryItemRequest item : placeOrderReq.getOrderSummaryItemRequests()) {
            productVariantIds.add(item.getProductId());
        }

        List<CartItems> cartItems = cartItemsRepo
                .findByProductVariantIdInAndUserName(productVariantIds, userName);

        Map<String, CartItems> cartMap = new HashMap<>();

        for (CartItems cartItem : cartItems) {
            cartMap.put(cartItem.getProductVariant().getId(), cartItem);
        }


        List<ProductVariant> variants = productVariantRepo.findAllById(productVariantIds);
        if (variants.size() != productVariantIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Có sản phẩm không tồn tại");
        }

        Map<String, ProductVariant> variantMap = new HashMap<>();

        for (ProductVariant variant : variants) {
            variantMap.put(variant.getId(), variant);
        }

        List<Inventory> inventoryList = inventoryRepo.findByProductVariantIdIn(productVariantIds);

        Map<String, Inventory> inventoryMap = new HashMap<>();

        for (Inventory inventory : inventoryList) {
            inventoryMap.put(inventory.getProductVariant().getId(), inventory);
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        List<Inventory> inventoriesToUpdate = new ArrayList<>();
        for (OrderSummaryItemRequest item : placeOrderReq.getOrderSummaryItemRequests()) {

            CartItems cartItem = cartMap.get(item.getProductId());
            if (cartItem == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm không tồn tại trong giỏ hàng");
            }

            if (!cartItem.getQuantity().equals(item.getQuantity())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số lượng sản phẩm trong giỏ hàng đã thay đổi");
            }

            ProductVariant variant = variantMap.get(item.getProductId());

            Inventory inventory = inventoryMap.get(item.getProductId());
            if (inventory == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không có trong kho");
            }

            long available = inventory.getQuantityInStock() - inventory.getReservedQuantity();
            if (available < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sản phẩm chỉ còn " + available);
            }
            subtotal = subtotal.add(variant.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());
            inventoriesToUpdate.add(inventory);

        }

        BigDecimal discountAmount = BigDecimal.ZERO;
        CouponsResponse coupons = couponsService.getCoupons(placeOrderReq.getCouponCode());

        if (coupons.getCouponType() == Type.PERCENT) {
            discountAmount = subtotal.multiply(coupons.getValue())
                    .divide(BigDecimal.valueOf(100));
        } else discountAmount = coupons.getValue();

        BigDecimal shippingFee = new BigDecimal("30000");
        BigDecimal grandTotal = subtotal.subtract(discountAmount).add(shippingFee);

        Coupons coupons1 = couponsRepo.findById(coupons.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VÉ giảm giá không có trong kho"));

        Orders order = orderMapper.toOrders(user, address, paymentMethod, coupons1,
                subtotal, discountAmount, shippingFee, grandTotal);
        ordersRepo.save(order);

        List<OrderItems> orderItemsList = new ArrayList<>();
        for (OrderSummaryItemRequest item : placeOrderReq.getOrderSummaryItemRequests()) {

            ProductVariant productVariant = variantMap.get(item.getProductId());
            OrderItems orderItem = new OrderItems();

            orderItem.setOrder(order);
            orderItem.setProductVariant(productVariant);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(productVariant.getPrice());

            orderItemsList.add(orderItem);
        }

        orderItemsRepo.saveAll(orderItemsList);
        inventoryRepo.saveAll(inventoriesToUpdate);


        if (placeOrderReq.getOrderSource() == OrderSource.CART) {

            List<CartItems> cartItemss = cartItemsRepo.findByProductVariantIdInAndUserName(productVariantIds, userName);

            for (CartItems cartItem : cartItems) {
                cartItem.setIsDeleted(true);
            }
            cartItemsRepo.saveAll(cartItemss);
        }

        // 11. Tạo tracking log đầu tiên
        TrackingLogs trackingLog = trackingLogMapper.toTrackingLog(order, user, WorkflowStatus.PENDING,
                "Đơn hàng vừa được tạo qua Mua Ngay", LocalDateTime.now());
        trackingLogRepo.save(trackingLog);

        PlaceOrderResponse response = placeOrderMapper.toResponse(order);
        return response;
    }
}
