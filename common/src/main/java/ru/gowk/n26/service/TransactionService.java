package ru.gowk.n26.service;

import ru.gowk.n26.domain.Transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public interface TransactionService {
    /**
     * Adds transaction into statistic.
     *
     * @param transaction valid transaction to add
     * @return true if the transaction was added or was scheduled to add, false if the transaction is too old
     */
    boolean createTransaction(Transaction transaction);
}
