package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers( "/register", "/login", "/preview").permitAll()
                        .requestMatchers("/authenticatedPreview").authenticated()
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/authenticatedPreview", true)
                        .permitAll())

                .logout(LogoutConfigurer::permitAll);

        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails samyak =
                User.builder()
                        .username("samyak")
                        .password(passwordEncoder().encode("123"))
                        .roles("USER")
                        .build();
        UserDetails rohan =
                User.builder()
                        .username("rohan")
                        .password(passwordEncoder().encode("123"))
                        .roles("USER")
                        .build();
        UserDetails mohan =
                User.builder()
                        .username("mohan")
                        .password(passwordEncoder().encode("123"))
                        .roles("USER")
                        .build();


        return new InMemoryUserDetailsManager(samyak, rohan, mohan);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
