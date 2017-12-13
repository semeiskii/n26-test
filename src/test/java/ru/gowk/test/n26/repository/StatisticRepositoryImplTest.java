package ru.gowk.test.n26.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.test.n26.domain.Statistic;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Vyacheslav Gorbatykh
 * @since 12.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticRepositoryImplTest {
    @Autowired
    private StatisticRepository repository;
//-----------------------------------------------------------------------------

    @Test
    public void shouldReturnZeroStatWhenNoTransactions() throws Exception {
        Statistic statistic = repository.getStatistic();

        assertNotNull("Statistic is null", statistic);
        assertEquals("Statistic sum is not 0", BigDecimal.ZERO, statistic.getSum());
        assertEquals("Statistic avg is not 0", 0D, statistic.getAvg(), 0D);
        assertEquals("Statistic max is not 0", BigDecimal.ZERO, statistic.getMax());
        assertEquals("Statistic min is not 0", BigDecimal.ZERO, statistic.getMin());
        assertEquals("Statistic avg is not 0", 0L, statistic.getCount());
    }

    @Test
    public void shouldReturnSameStatValuesAsOneTransaction() throws Exception {
    }

    @Test
    public void shouldReturnProperStatForTransactionsWithTheSameTime() throws Exception {
    }

    @Test
    public void shouldReturnProperStatForTransactionsWithDifferentTime() throws Exception {
    }

}