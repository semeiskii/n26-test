package ru.gowk.n26.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.n26.core.CoreConfiguration;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static ru.gowk.n26.util.TestUtil.transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest("core.statisticPeriod=60000")
public class TransactionServiceImplDelayedTest {
    @MockBean
    private StatisticRepository repository;

    @Autowired
    private TransactionService service;
    @Autowired
    private DateUtil dateUtil;
//-----------------------------------------------------------------------------

    @Test
    public void shouldAddTrasactionInFuture() {
        Transaction transaction = transaction(12.34, dateUtil.getCurrentTime() + 500);
        assertTrue(service.createTransaction(transaction));

        verify(repository, timeout(1000).times(1)).addTransaction(transaction);
    }
//-----------------------------------------------------------------------------

    @SpringBootApplication
    @Import(CoreConfiguration.class)
    static class TestConfiguration {
    }
}