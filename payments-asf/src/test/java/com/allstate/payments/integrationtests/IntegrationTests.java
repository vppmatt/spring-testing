package com.allstate.payments.integrationtests;

import com.allstate.payments.data.CreditCardTransactionRepository;
import com.allstate.payments.domain.CreditCardTransaction;
import com.allstate.payments.dtos.CreditCardTransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CreditCardTransactionRepository repository;

    @Test
    public void testThatPostingANewTransactionGetsAddedToTheDatabase() throws Exception {

        CreditCardTransactionDTO transaction = new CreditCardTransactionDTO();
        transaction.setOrderId("223344");
        transaction.setCountry("USA");
        transaction.setAmount(17.33);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(transaction);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/payment")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        CreditCardTransaction expectedTransaction = new CreditCardTransaction();
        expectedTransaction.setOrderId("223344");
        expectedTransaction.setCountry("USA");
        expectedTransaction.setAmount(17.33);
        expectedTransaction.setId(1);

        String expectedJSON = om.writeValueAsString(expectedTransaction);

        ResultActions result = mockMvc.perform(request)
                .andExpect(status().isOk());

        ArgumentCaptor<CreditCardTransaction> capturedTransaction = ArgumentCaptor.forClass(CreditCardTransaction.class);

        Mockito.verify(repository).save(capturedTransaction.capture());
        assertEquals("223344", capturedTransaction.getValue().getOrderId());

    }

}
