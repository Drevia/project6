package com.project.paymybuddy.controller;

import com.project.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class TransactionControllerTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("emailTest")
    void shouldCreateTransaction() throws Exception {
        mockMvc.perform(post("/transfer")
                        .with(csrf())
                        .param("receiverId", "2")
                        .param("amount", "10"))
                .andDo(print())
                .andExpect(redirectedUrl("/transfer"))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, transactionRepository.findAllByGiverId(1).size());

    }

    @Test
    void shouldReturnErrorWhenTransactionNotValid() throws Exception {

        mockMvc.perform(post("/transfer")
                        .param("receiverId", "blablabla")
                        .param("amount", "10"))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

}
