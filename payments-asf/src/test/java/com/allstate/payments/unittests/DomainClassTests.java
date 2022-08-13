package com.allstate.payments.unittests;

import com.allstate.payments.domain.CreditCardTransaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DomainClassTests {

    @Test
    public void testEqualityOfTransactionsUsingIDOnly() {
        CreditCardTransaction transaction1 = new CreditCardTransaction();
        transaction1.setId(17);
        CreditCardTransaction transaction2 = new CreditCardTransaction();
        transaction2.setId(17);
        assertEquals(transaction1, transaction2);
    }
}
