
package com.LOGIN.login.page.Security;

 import com.LOGIN.login.page.Service.CustomUserDetailsService;
 import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Security configuration ko enable karega
public class SecurityConfig {

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder: Industry standard password hashing ke liye
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Authorization Rules
                .authorizeHttpRequests(authorize -> authorize
                        // /register, /req/register, /login, aur static assets sabko access karne
                        .requestMatchers("/api/req/register","/api/register", "/api/login" ).permitAll()
                        .requestMatchers("/entries/**").hasRole("OWNER")
                        // 🔑 CRITICAL: /journal ko sirf authenticated users ke liye allow karein
                        .requestMatchers("/journal").authenticated()
                        // Baaki saari requests ko authenticated hona zaroori hai
                        .anyRequest().authenticated()
                )

                // 2. Form Login Configuration
                .formLogin(form -> form
                        .loginPage("/api/login") // Custom login page ka URL
                        // 🔑 CRITICAL: Success hone par naye Journal App par redirect karein
                        .defaultSuccessUrl("/api/journal", true) // true = always redirect to /journal
                        .failureUrl("/login?error") // Failure hone par /login?error par wapas bhej de
                        .permitAll()
                )

                // 3. Logout Configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Logout hone ke baad login page par bhej de
                        .permitAll()
                )

                // 4. CSRF Configuration: (AJAX registration ke liye disable rakha hai)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
