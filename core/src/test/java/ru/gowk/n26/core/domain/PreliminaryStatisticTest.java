package ru.gowk.n26.core.domain;

import org.junit.Test;
import ru.gowk.n26.domain.Transaction;

import static org.junit.Assert.assertEquals;
import static ru.gowk.n26.util.TestUtil.big;
import static ru.gowk.n26.util.TestUtil.transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
public class PreliminaryStatisticTest {
    @Test
    public void shouldConstructsProperlyWithTransaction() {
        double amount = 12.34;
        Transaction transaction = transaction(amount, 42);

        int domain = 54321;
        PreliminaryStatistic statistic = new PreliminaryStatistic(domain, transaction);

        assertEquals(big(amount), statistic.getSum());
        assertEquals(big(amount), statistic.getMax());
        assertEquals(big(amount), statistic.getMin());
        assertEquals(1, statistic.getCount());
        assertEquals(domain, statistic.getDomain());
    }
}