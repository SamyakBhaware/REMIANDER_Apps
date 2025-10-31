package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebControllerTwo implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/preview").setViewName("Preview");
//    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute UserRegistration userRegistration, Model model) {
        model.addAttribute("userRegistration", userRegistration);
         return "RegistrationForm";
    }

    @GetMapping("/hello")
    public String hello(){
        return "/hello";
    }

//    @PostMapping("/register")
//    public String checkUser(@ModelAttribute UserRegistration form, Model model) {
//        if (userRepository.existsByEmail(form.getEmail())) {
//            model.addAttribute("message", "User already exists!");
//            return "RegistrationForm";
//        }
//        userRepository.save(form);
//        model.addAttribute("message", "Registered successfully! Now login.");
//        return "RegistrationForm"; // yahi form reload hoti rahegi message ke saath
//    }

//    @GetMapping("/greeting")
//    public String greeting(){
//        return "/cssAndJs/greeting";
//    }
//
//    @GetMapping("/register")
//    public String registerPage()
//    {
//        return "RegistrationForm";
//    }
}
