package com.project.paymybuddy.controller;

import com.project.paymybuddy.config.TestConfig;
import com.project.paymybuddy.repository.FriendshipRepository;
import com.project.paymybuddy.repository.UserRepository;
import com.project.paymybuddy.service.TransactionReadService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginPageController.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@AutoConfigureMockMvc
public class LoginPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Inject
    UserRepository userRepository;

    @Test
    public void testLoginPageOk() throws Exception {
        mockMvc.perform(get("/login")).andDo(print())
                .andExpect(view().name("login"));
    }
}
