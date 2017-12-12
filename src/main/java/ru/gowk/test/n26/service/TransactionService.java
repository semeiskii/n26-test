package ru.gowk.test.n26.service;

import ru.gowk.test.n26.domain.Transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public interface TransactionService {
    void createTransaction(Transaction transaction);
}
