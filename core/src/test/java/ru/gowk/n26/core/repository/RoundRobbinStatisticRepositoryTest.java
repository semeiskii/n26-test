package ru.gowk.n26.core.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.n26.core.CoreConfiguration;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.domain.Transaction;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static ru.gowk.n26.util.TestUtil.big;
import static ru.gowk.n26.util.TestUtil.transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest("core.statisticPeriod=10")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoundRobbinStatisticRepositoryTest {

    @MockBean
    private DateUtil dateUtil;

    @Autowired
    private StatisticRepository repository;
//-----------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        given(dateUtil.getCurrentTime()).willReturn(42L);
    }
//-----------------------------------------------------------------------------

    @Test
    public void shouldReturnEmptyStatisticWhenNoTransactions() {
        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(BigDecimal.ZERO, statistic.getSum());
        assertEquals(BigDecimal.ZERO, statistic.getMax());
        assertEquals(BigDecimal.ZERO, statistic.getMin());
        assertEquals(0, statistic.getCount());
    }

    @Test
    public void shouldReturnSameValuesForOneTransaction() {
        double amount = 12.34;
        Transaction transaction = transaction(amount, 41);
        repository.addTransaction(transaction);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount), statistic.getSum());
        assertEquals(big(amount), statistic.getMax());
        assertEquals(big(amount), statistic.getMin());
        assertEquals(1, statistic.getCount());
    }

    @Test
    public void shouldSummariseValuesForSeveralTransactions() {
        double amount1 = 12.34;
        Transaction transaction1 = transaction(amount1, 40);
        repository.addTransaction(transaction1);

        double amount2 = 56.78;
        Transaction transaction2 = transaction(amount2, 41);
        repository.addTransaction(transaction2);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount1).add(big(amount2)), statistic.getSum());
        assertEquals(big(amount2), statistic.getMax());
        assertEquals(big(amount1), statistic.getMin());
        assertEquals(2, statistic.getCount());
    }

    @Test
    public void shouldSummariseValuesWhenOneTransactionRepeated() {
        double amount = 12.23;
        Transaction transaction = transaction(amount, 41);
        repository.addTransaction(transaction);
        repository.addTransaction(transaction);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount).add(big(amount)), statistic.getSum());
        assertEquals(big(amount), statistic.getMax());
        assertEquals(big(amount), statistic.getMin());
        assertEquals(2, statistic.getCount());
    }

    @Test
    public void shouldNotGetValuesFromOldTransaction() {
        double amount1 = 12.23;
        Transaction transaction1 = transaction(amount1, 32);
        repository.addTransaction(transaction1);

        double amount2 = 45.56;
        Transaction transaction2 = transaction(amount2, 33);
        repository.addTransaction(transaction2);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount2), statistic.getSum());
        assertEquals(big(amount2), statistic.getMax());
        assertEquals(big(amount2), statistic.getMin());
        assertEquals(1, statistic.getCount());
    }

    @Test
    public void shouldReplaceOldTransactionWithNewOne() {
        double amount1 = 12.23;
        Transaction transaction1 = transaction(amount1, 31);
        repository.addTransaction(transaction1);

        double amount2 = 45.56;
        Transaction transaction2 = transaction(amount2, 33);
        repository.addTransaction(transaction2);

        double amount3 = 78.90;
        Transaction transaction3 = transaction(amount3, 41);
        repository.addTransaction(transaction3);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount2).add(big(amount3)), statistic.getSum());
        assertEquals(big(amount3), statistic.getMax());
        assertEquals(big(amount2), statistic.getMin());
        assertEquals(2, statistic.getCount());
    }

    @Test
    public void shouldSkipOldTransaction() {
        double amount1 = 12.23;
        Transaction transaction1 = transaction(amount1, 35);
        repository.addTransaction(transaction1);

        double amount2 = 45.56;
        Transaction transaction2 = transaction(amount2, 37);
        repository.addTransaction(transaction2);

        given(dateUtil.getCurrentTime()).willReturn(46L);

        Statistic statistic = repository.getStatistic();

        assertNotNull(statistic);
        assertEquals(big(amount2), statistic.getSum());
        assertEquals(big(amount2), statistic.getMax());
        assertEquals(big(amount2), statistic.getMin());
        assertEquals(1, statistic.getCount());
    }
//-----------------------------------------------------------------------------

    @SpringBootApplication
    @Import(CoreConfiguration.class)
    static class TestConfiguration {
    }
}
