package com.example.trackingorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

//    // sẽ bảo security rằng sẽ sủ dụng báic auth làm xác thực và tất cả các APi Đêù cần bảo vệ
//    // user name và pass nào sẽ đi vào hệ thống
//    @Bean
//    public SecurityFilterChain configSecurity(HttpSecurity httpSecurity){
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth ->
//                        auth
//                                .requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
//                                .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults()) ;
//        // Dang nhap qua gg : oauth2
//        //basic-auth : moi request deu phai truyen username-password
//        return httpSecurity.build() ;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder (){
//        return new BCryptPasswordEncoder() ;
//    }
}
