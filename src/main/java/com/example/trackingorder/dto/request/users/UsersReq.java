package com.example.trackingorder.dto.request.users;

import com.example.trackingorder.status.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsersReq {
    @NotBlank(message = "Username không được để trống")
    private String userName ;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2 đến 100 ký tự")
    @Pattern(
            regexp = "^[\\p{L} ]+$",
            message = "Họ tên chỉ được chứa chữ cái và khoảng trắng"
    )
    private String fullName ;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6 ,max = 50, message = "Mật khẩu phải từ 6 đến 50 ký tự")
    private String passWord ;


    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email ;

    @NotBlank(message = "Số điện thoại  không được để trống")
    private String phone ;

    @NotNull(message = "Role không được để trống")
    private Role role ;
}
