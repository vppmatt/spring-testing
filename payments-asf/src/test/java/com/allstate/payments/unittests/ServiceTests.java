package com.allstate.payments.unittests;

import com.allstate.payments.control.PaymentsController;
import com.allstate.payments.data.CreditCardTransactionRepository;
import com.allstate.payments.domain.CreditCardTransaction;
import com.allstate.payments.service.PaymentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ServiceTests {

    @Autowired
    PaymentsService paymentsService;

    @MockBean
    CreditCardTransactionRepository repository;

    @BeforeEach
    public void setup() {
        List<CreditCardTransaction> transactions = new ArrayList<>();
        transactions.add(new CreditCardTransaction(1,10.2,"USA","USD", LocalDate.now(),"1",1,0.2,"VISA" ));
        transactions.add(new CreditCardTransaction(2,10.2,"USA","USD", LocalDate.now(),"1",1,0.2,"VISA" ));
        transactions.add(new CreditCardTransaction(3,10.2,"USA","USD", LocalDate.now(),"1",1,0.2,"VISA" ));
        Mockito.when(repository.findAll()).thenReturn(transactions);
    }

    @Test
    public void testGetNumberOfPaymentsReturnsTheCorrectValue() {
        assertEquals(3, paymentsService.countTransactions());
    }
}
