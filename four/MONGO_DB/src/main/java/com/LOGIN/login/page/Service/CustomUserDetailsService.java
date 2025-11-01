


package com.LOGIN.login.page.Service;

import com.LOGIN.login.page.Model.UserData;
import com.LOGIN.login.page.Model.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {

        // IITian Concept: Spring Security hamesha is parameter ko 'username' ke naam se hi bhejta hai,
        // bhale hi hum use email ki tarah treat kar rahe hain. Isliye hum findByEmail() use kar rahe hain.
        UserData user = userRepository.findByEmail(emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailOrUsername));

        // Spring Security ke UserDetails object ko build karna
        // Note: Roles/Authorities ko simple rakha hai (Collections.emptyList())
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Spring Security ka username field (we use the actual email here)
                user.getPassword(), // Hashed Password
                Collections.emptyList() // Authorities (Roles)
        );
    }
}

