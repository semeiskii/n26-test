package ru.gowk.test.n26.repository;

import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.domain.Transaction;

import java.util.List;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public interface StatisticRepository {
    void createTransaction(Transaction transaction);

    Statistic getStatistic();
}
