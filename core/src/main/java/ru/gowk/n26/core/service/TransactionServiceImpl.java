package ru.gowk.n26.core.service;

import org.springframework.stereotype.Service;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private final StatisticRepository repository;

    public TransactionServiceImpl(StatisticRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        repository.addTransaction(transaction);
    }
}
