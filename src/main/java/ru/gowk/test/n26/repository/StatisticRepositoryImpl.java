package ru.gowk.test.n26.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.domain.Transaction;

import javax.annotation.PostConstruct;
import java.time.Instant;

/**
 * @author Vyacheslav Gorbatykh
 * @since 12.12.2017
 */
@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
    @Value("${statistics.period}")
    private int statisticsPeriod;

    private DomainStat[] statistics;
//-----------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        statistics = new DomainStat[statisticsPeriod];
        for (int i = 0; i < statistics.length; i++) {
            statistics[i] = new DomainStat();
        }
    }
//-----------------------------------------------------------------------------

    @Override
    public void createTransaction(Transaction transaction) {
        long domain = transaction.getTimestamp() / statisticsPeriod;
        int index = (int) (transaction.getTimestamp() - domain);

        DomainStat statistic = statistics[index];
        accumulateTransaction(domain, statistic, transaction);
    }

    @Override
    public Statistic getStatistic() {
        Statistic result = new Statistic();

        long currentTime = getCurrentTime();
        long currentDomain = currentTime / statisticsPeriod;
        long previousDomain = currentDomain - 1;
        int currentIndex = (int) (currentTime - currentDomain);
        for (int i = 0; i < statistics.length; i++) {
            DomainStat stat = statistics[i];
            if (i <= currentIndex) {
                if (stat.getDomain() != currentDomain) {
                    continue;
                }
            } else {
                if (stat.getDomain() != previousDomain) {
                    continue;
                }
            }
            accumulateDomainStatistic(result, stat);
        }

        result.calculateAverage();

        return result;
    }
//-----------------------------------------------------------------------------

    private void accumulateTransaction(long domain, DomainStat statistic, Transaction transaction) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (statistic) {
            if (statistic.getDomain() != domain) {
                statistic.clear();
                statistic.setDomain(domain);
            }
            statistic.accumulate(transaction.getAmount());
        }
    }

    private void accumulateDomainStatistic(Statistic result, DomainStat statistic) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (statistic) {
            result.accumulate(statistic);
        }
    }

    private long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }
}
