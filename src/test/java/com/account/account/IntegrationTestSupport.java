package com.account.account;

import com.account.account.dto.converter.CustomerDTOConverter;
import com.account.account.repository.CustomerRepository;
import com.account.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


public abstract class IntegrationTestSupport extends TestSupport {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public AccountService accountService;

    @Autowired
    public CustomerDTOConverter converter;

    public final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}