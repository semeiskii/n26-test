package ru.gowk.n26.core.repository;

import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.domain.Transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
public interface StatisticRepository {
    void addTransaction(Transaction transaction);

    Statistic getStatistic();
}
