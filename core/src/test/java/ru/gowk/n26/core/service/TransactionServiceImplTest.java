package ru.gowk.n26.core.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.n26.core.CoreConfiguration;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static ru.gowk.n26.util.TestUtil.transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest("core.statisticPeriod=10")
public class TransactionServiceImplTest {
    @MockBean
    private StatisticRepository repository;
    @MockBean
    private DateUtil dateUtil;
    @MockBean
    private TaskScheduler scheduler;

    @Autowired
    private TransactionService service;
//-----------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        given(dateUtil.getCurrentTime()).willReturn(42L);
    }
//-----------------------------------------------------------------------------

    @Test
    public void shouldNotAddTrasaction() {
        Transaction transaction = transaction(12.34, 32);
        assertFalse(service.createTransaction(transaction));

        verify(repository, never()).addTransaction(transaction);
        verify(scheduler, never()).schedule(any(RepeateTransactionCreationTask.class), any(Date.class));
    }

    @Test
    public void shouldUseRepositoryAddTrasaction() {
        Transaction transaction = transaction(12.34, 42);
        assertTrue(service.createTransaction(transaction));

        verify(repository, times(1)).addTransaction(transaction);
        verify(scheduler, never()).schedule(any(RepeateTransactionCreationTask.class), any(Date.class));
    }

    @Test
    public void shouldUseScheduler() {
        Transaction transaction = transaction(12.34, 43);
        assertTrue(service.createTransaction(transaction));

        verify(scheduler, times(1)).schedule(any(RepeateTransactionCreationTask.class), any(Date.class));
        verify(repository, never()).addTransaction(transaction);
    }
//-----------------------------------------------------------------------------

    @SpringBootApplication
    @Import(CoreConfiguration.class)
    static class TestConfiguration {
    }
}