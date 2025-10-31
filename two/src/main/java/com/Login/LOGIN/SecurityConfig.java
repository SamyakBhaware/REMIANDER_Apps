package com.Login.LOGIN;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Primary
public class SecurityConfig {

     @Bean
     @Primary
    HttpSecurity securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.
                        authorizeHttpRequests(auth->
                                auth.requestMatchers("/home.html").permitAll()
                                        .anyRequest().authenticated()
                        )
                        .csrf(csrf -> csrf.disable())
                        .formLogin( Customizer.withDefaults());
    }

    @Bean
    UserDetailsService userDetailsService() {
        var user = User.withUsername("Samyak")
                .password("{noop}111")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
