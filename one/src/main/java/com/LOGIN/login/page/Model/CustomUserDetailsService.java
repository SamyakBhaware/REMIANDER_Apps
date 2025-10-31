//package com.LOGIN.login.page.Model;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<UserData> user =  userRepository.findByEmail(email);
//        if(user.isPresent()){
//            var userData = user.get();
//            return User.builder()
//                    .username(userData.getEmail())
//                    .password(userData.getPassword())
//                    .build();
//        }
//        else{
//            throw new UsernameNotFoundException(email);
//        }
//    }
//
//}
//

















package com.LOGIN.login.page.Model;

import com.LOGIN.login.page.Model.UserData;
import com.LOGIN.login.page.Model.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Responsible for loading user-specific data during the authentication process.
 * This service is configured to load the user based solely on the email field.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username provided in the login form.
     * Since we are using email for login, the incoming 'emailOrUsername' parameter
     * is treated as the user's email address.
     *
     * @param emailOrUsername The value entered by the user in the 'username' field of the login form (which is email).
     * @return A UserDetails object that Spring Security can use for password verification.
     * @throws UsernameNotFoundException if the user is not found.
     */
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

