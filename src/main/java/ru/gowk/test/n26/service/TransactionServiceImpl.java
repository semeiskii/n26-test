package ru.gowk.test.n26.service;

import org.springframework.stereotype.Service;
import ru.gowk.test.n26.domain.Transaction;
import ru.gowk.test.n26.repository.StatisticRepository;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private StatisticRepository repository;
//-----------------------------------------------------------------------------

    public TransactionServiceImpl(StatisticRepository repository) {
        this.repository = repository;
    }
//-----------------------------------------------------------------------------

    @Override
    public void createTransaction(Transaction transaction) {
        repository.createTransaction(transaction);
    }
}
