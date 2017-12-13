package ru.gowk.n26.core.repository;

import org.springframework.stereotype.Repository;
import ru.gowk.n26.core.domain.PreliminaryStatistic;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.domain.Transaction;

import java.util.stream.IntStream;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Repository
public class RoundRobbinStatisticRepository implements StatisticRepository {
    private final DateUtil dateUtil;

    private final PreliminaryStatistic[] statistics;
//-----------------------------------------------------------------------------

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public RoundRobbinStatisticRepository(DateUtil dateUtil, int statisticPeriod) {
        this.dateUtil = dateUtil;

        statistics = new PreliminaryStatistic[statisticPeriod];
        for (int i = 0; i < statistics.length; i++) {
            statistics[i] = new PreliminaryStatistic();
        }
    }
//-----------------------------------------------------------------------------

    public void addTransaction(Transaction transaction) {
        long domain = getDomain(transaction.getTimestamp());
        int index = getIndex(transaction.getTimestamp());

        PreliminaryStatistic statistic = statistics[index];
        accumulate(statistic, new PreliminaryStatistic(domain, transaction));
    }

    public Statistic getStatistic() {
        Statistic result = new Statistic();

        long currentTime = dateUtil.getCurrentTime();
        long currentDomain = getDomain(currentTime);
        int currentIndex = getIndex(currentTime);

        IntStream.range(0, statistics.length)
                .forEach(i -> accumulate(result, currentDomain, currentIndex, statistics[i], i));

        return result;
    }
//-----------------------------------------------------------------------------

    private int getIndex(long timestamp) {
        return (int) (timestamp % statistics.length);
    }

    private long getDomain(long timestamp) {
        return timestamp / statistics.length;
    }

    private void accumulate(PreliminaryStatistic result, PreliminaryStatistic source) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (result) {
            if (result.getDomain() != source.getDomain()) {
                result.init();
                result.setDomain(source.getDomain());
            }
            result.accumulate(source);
        }
    }

    private void accumulate(Statistic result, long currentDomain, int currentIndex, PreliminaryStatistic source, int sourceIndex) {
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (source) {
            if (sourceIndex <= currentIndex) {
                if (source.getDomain() != currentDomain) {
                    return;
                }
            } else {
                if (source.getDomain() != (currentDomain - 1)) {
                    return;
                }
            }

            result.accumulate(source);
        }
    }
}
