package com.example.trackingorder.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerException(MethodArgumentNotValidException exception) {
        log.error("Exception là ", exception);


        Map<String, String> errors = new HashMap<>();

        BindingResult result = exception.getBindingResult();// nơi chứa tất cả các lỗi của valid

        List<FieldError> fieldErrors = result.getFieldErrors(); // lấy ra từng lỗi cho vào list

        for (FieldError error : fieldErrors) {
            String fieldName = error.getField(); // name, password
            String message = error.getDefaultMessage(); // message viết trong DTO

            errors.put(fieldName, message);
        }

        ErrorResponse error = new ErrorResponse();
        error.setCode(400);
        error.setMessage("Lỗi Hệ Thống");
        error.setErrors(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
