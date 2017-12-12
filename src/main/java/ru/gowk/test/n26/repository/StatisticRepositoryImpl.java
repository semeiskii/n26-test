package ru.gowk.test.n26.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.domain.Transaction;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vyacheslav Gorbatykh
 * @since 12.12.2017
 */
@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
    @Value("${statistics.period}")
    private int statisticsPeriod;

    private Statistic[] statistics;
    private int index;
//-----------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        statistics = new Statistic[statisticsPeriod + 1];
    }
//-----------------------------------------------------------------------------

    @Override
    public void createTransaction(Transaction transaction) {
        Statistic statistic = new Statistic();
        statistic.setSum(transaction.getAmount());
        statistic.setMin(transaction.getAmount());
        statistic.setMax(transaction.getAmount());
        statistic.setCount(1);
        statistics[index] = statistic;
        index++;
    }

    @Override
    public List<Statistic> getPreliminaryStatistic() {
        return Arrays.asList(statistics);
    }
}
