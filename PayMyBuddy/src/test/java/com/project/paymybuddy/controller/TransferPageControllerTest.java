package com.project.paymybuddy.controller;

import com.project.paymybuddy.model.TransactionReadDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class TransferPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("emailTest2")
    public void testTransferPageOk() throws Exception {
        TransactionReadDto transactionReadDto = new TransactionReadDto();
        transactionReadDto.setConnexionsName("firstNameTest");
        transactionReadDto.setDescription("description");
        transactionReadDto.setAmount(50.0f);

        List<TransactionReadDto> transactionsReadDto = new ArrayList<>();
        transactionsReadDto.add(transactionReadDto);

        mockMvc.perform(get("/transfer")).andDo(print())
                .andExpect(view().name("transfer"))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("transactionsRead", transactionsReadDto));
    }
}
