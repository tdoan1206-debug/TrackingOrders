package com.example.trackingorder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CustomUserService {

//    private final UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        PersonUsers user = userRepo.findByUserName(username) ;
//        if(user == null ){
//            throw new UsernameNotFoundException("khong tim thay user") ;
//        }
//
//        String role = user.getRole() ;
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role) ;
//
//        return new User(username, user.getPassword(), List.of(authority ));
//    }
}
