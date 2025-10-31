package com.LOGIN.login.page.Controller;

import com.LOGIN.login.page.Model.UserData;
import com.LOGIN.login.page.Model.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/api")
    public class ContentController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 👈 New dependency

     public ContentController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Initialize karo
    }

    @GetMapping("/login-page")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public @ResponseBody String register(@RequestBody UserData user){

        Optional<UserData> foundUser = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent()){
            return "User already registered. Please login or use a different email.";        }
        else{
            try {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword); // Hashed password set karo

                userRepository.save(user);
                return "success";
            } catch(Exception e){
                return "error: " + e.getMessage();
            }
        }
    }

    @GetMapping("/journal")
    public String journal(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName()); // user ka email ya username bhejta hai
        } else {
            model.addAttribute("username", "Guest");
        }
        return "journal"; // Thymeleaf template name
    }

    @PostMapping("/login")
    public String getJournal(){
        return "journal";
    }




}
