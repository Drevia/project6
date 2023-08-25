package com.project.paymybuddy.controller;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import com.project.paymybuddy.service.TransactionReadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginPageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionReadService transactionReadService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/transfer")
    public String showTransferPage(Model model) {
        AppUser appUser = userRepository.findById(1).orElseThrow();
        List<TransactionReadDto> transactionReadDtoList = new ArrayList<>();
        transactionReadDtoList = transactionReadService.getAllTransactionRead(appUser);

        model.addAttribute("transactionsRead", transactionReadDtoList);
        return "transfer";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        AppUser appUser = userRepository.findByEmail(email);

        if (appUser != null && appUser.getPassword().equals(password)) {
            return "redirect:/transfer";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
}
