package com.project.paymybuddy.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.paymybuddy.config.TestConfig;
import com.project.paymybuddy.repository.TransactionRepository;
import com.project.paymybuddy.repository.UserRepository;
import com.project.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.*;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@ActiveProfiles("test")
@Import(TestConfig.class)
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

    @Spy
    ObjectMapper objectMapper;


    @BeforeEach
    void init(){
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        File jsonFile = new ClassPathResource("init/transaction.json").getFile();
        final String transactionToCreate = Files.readString(jsonFile.toPath());

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionToCreate))
                .andDo(print()).andExpect(status().isCreated());

        Assertions.assertEquals(1, transactionRepository.findAllByGiverId_Id(1).size());
    }

    @Test
    void shouldReturnErrorWhenTransactionNotValid() throws Exception {
        File jsonFile = new ClassPathResource("init/transactionNotValid.json").getFile();
        final String transactionToCreate = Files.readString(jsonFile.toPath());

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionToCreate))
                .andDo(print()).andExpect(status().is4xxClientError());

        Assertions.assertEquals(0, transactionRepository.findAllByGiverId_Id(1).size());
    }

}
