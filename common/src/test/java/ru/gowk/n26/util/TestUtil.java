package ru.gowk.n26.util;

import ru.gowk.n26.domain.Transaction;

import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
public class TestUtil {
    public static BigDecimal big(double amount) {
        return BigDecimal.valueOf(amount);
    }

    public static Transaction transaction(double amount, long timestamp) {
        Transaction transaction = new Transaction();
        transaction.setAmount(big(amount));
        transaction.setTimestamp(timestamp);
        return transaction;
    }
}
