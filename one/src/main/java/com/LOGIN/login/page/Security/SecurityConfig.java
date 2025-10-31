//package com.LOGIN.login.page.Security;
//
//import com.LOGIN.login.page.Model.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final UserService userService;
//    public SecurityConfig(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userService;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        return http
////                .csrf(AbstractHttpConfigurer::disable)
////                .formLogin(form -> form
////                        .loginPage("/login") // Custom login page ka URL
////                        .loginProcessingUrl("/login")
////                        .defaultSuccessUrl("/home", true)// Form submit hone par kahan jaana hai
////                        .permitAll() // Login page ko sabko allow karo
////                )
////                .authorizeHttpRequests(registry-> registry
////
////                        // 1. Static resources ko poora allow karo
////                        // /css/ aur /js/ ke andar ki sab files allow hongi
////                        .requestMatchers("/css/**", "/js/**").permitAll()
////
////                        // 2. Public pages (Controller mappings) ko allow karo
////                        .requestMatchers("/login", "/req/register", "/req/signup",  "/signup", "/register").permitAll()
////
////                        .requestMatchers("/home").authenticated()
////
////                        // 3. Baaki sabke liye login zaroori hai
////                        .anyRequest().authenticated()
////                )
////
////
////                .build();
////    }
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        // 1. Static resources open
//                        .requestMatchers("/css/**", "/js/**").permitAll()
//
//                        // 2. Public pages open (register/signup/login)
//                        .requestMatchers(
//                                "/login", "/req/login",       // login pages
//                                "/signup", "/req/signup",     // signup pages
//                                "/register", "/req/register"  // register pages
//                        ).permitAll()
//
//                        // 3. Home page only for authenticated users
//                        .requestMatchers("/home").authenticated()
//
//                        // 4. Baaki sab ke liye login required
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .permitAll()
//                )
//                .build();
//    }
//
//
//}
























package com.LOGIN.login.page.Security;

 import com.LOGIN.login.page.Model.CustomUserDetailsService;
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

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
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
                        .requestMatchers("/register", "/login" ).permitAll()
                        // 🔑 CRITICAL: /journal ko sirf authenticated users ke liye allow karein
                        .requestMatchers("/journal").authenticated()
                        // Baaki saari requests ko authenticated hona zaroori hai
                        .anyRequest().authenticated()
                )

                // 2. Form Login Configuration
                .formLogin(form -> form
                        .loginPage("/api/register") // Custom login page ka URL
                        .loginProcessingUrl("api/register") // Jahaan form POST hoga
                        // 🔑 CRITICAL: Success hone par naye Journal App par redirect karein
                        .defaultSuccessUrl("/journal", true) // true = always redirect to /journal
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
