package com.project.paymybuddy.controller;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.model.Friendship;
import com.project.paymybuddy.model.TransactionReadDto;
import com.project.paymybuddy.repository.FriendshipRepository;
import com.project.paymybuddy.service.TransactionReadService;
import com.project.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TransferPageController {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private TransactionReadService transactionReadService;

    @Autowired
    private UserService userService;
    @GetMapping("/transfer")
    public String showTransferPage(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<TransactionReadDto> pagedTransactions = transactionReadService.getPagedTransactions(pageable);

        AppUser appUser = userService.getUser();
        List<TransactionReadDto> transactionReadDtoList;
        transactionReadDtoList = transactionReadService.getAllTransactionRead(appUser);
        List<Friendship> friendshipList = friendshipRepository.findAllByAppUserOriginId_Id(1);
        System.out.println(friendshipList);


        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages",pagedTransactions.getTotalPages());
        model.addAttribute("transactionsPage", pagedTransactions);
        model.addAttribute("transactionsRead", transactionReadDtoList);
        model.addAttribute("connections", friendshipList);
        return "transfer";
    }
}
