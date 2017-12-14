package ru.gowk.n26.core.repository;

import org.springframework.stereotype.Repository;
import ru.gowk.n26.core.domain.PreliminaryStatistic;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.domain.Transaction;

import java.util.stream.IntStream;

/**
 * Domain and index.
 * To drop old transactions from statistics we use domain and index. See {@link #getDomain(long)} and
 * {@link #getIndex(long)}.
 * When we add new transaction, we store it in appropriate item and set it's domain.
 * Then, when we receive statistics, we use domain to understand - is this item contains statistic for last
 * statisticPeriod.
 * <p>
 * For example, statisticPeriod = 60000 and current time is 1513210240500. Index would be 500 and domain - 25220170
 * <p>
 * All items in {@link #statistics} from index 0 to index 500 (including) are milliseconds of this minute within
 * current time window. So the domain value of items should be the same as the domain value of current time. If not -
 * it's an old statistic and we should skip it.
 * <p>
 * And all items in {@link #statistics} from 500 (excluding) to the end of array are milliseconds of previous minute
 * within current time window. So the domain value of items should be the same as the (domain value of current time -
 * 1). If not - it's an old statistic and we should skip it.
 *
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

    /**
     * index - it's a number of statistic's item where we should store the transaction with this timestamp.
     */
    private int getIndex(long timestamp) {
        return (int) (timestamp % statistics.length);
    }

    /**
     * domain - it's a serial number of "time window" for which corresponds the transaction with this timestamp.
     * For example, if statisticPeriod=1min, then this is the serial number of minute in epoch.
     */
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

    private void accumulate(Statistic result, long currentDomain, int currentIndex, PreliminaryStatistic source,
                            int sourceIndex) {
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
