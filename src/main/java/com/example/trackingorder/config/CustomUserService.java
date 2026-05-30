package com.example.trackingorder.config;

import com.example.trackingorder.entity.users.Users;

import com.example.trackingorder.repository.UserRepo;
import com.example.trackingorder.status.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepo.findByUserName(userName);
        if (optionalUsers == null) {
            throw new UsernameNotFoundException("khong tim thay user");
        }
        Users user = optionalUsers.get();
        Role role = user.getRole();
        String role2 = role.name();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role2);

        return new User(userName, user.getPassWord(), List.of(authority));
    }

//    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
//        System.out.println(passwordEncoder.encode("123456") );
//    }
}
