package ru.gowk.n26.service;

import ru.gowk.n26.domain.Transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public interface TransactionService {
    boolean createTransaction(Transaction transaction);
}
