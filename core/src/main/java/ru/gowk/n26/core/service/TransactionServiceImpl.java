package ru.gowk.n26.core.service;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private final StatisticRepository repository;
    private final DateUtil dateUtil;
    private final ApplicationContext context;
    private final TaskScheduler scheduler;
    private final int statisticPeriod;
//-----------------------------------------------------------------------------

    public TransactionServiceImpl(StatisticRepository repository, DateUtil dateUtil, ApplicationContext context,
                                  TaskScheduler scheduler, int statisticPeriod) {
        this.repository = repository;
        this.dateUtil = dateUtil;
        this.context = context;
        this.scheduler = scheduler;
        this.statisticPeriod = statisticPeriod;
    }
//-----------------------------------------------------------------------------

    @Override
    public boolean createTransaction(Transaction transaction) {
        long currentTime = dateUtil.getCurrentTime();
        if (transaction.getTimestamp() <= (currentTime - statisticPeriod)) {
            return false;
        }

        if (transaction.getTimestamp() > currentTime) {
            scheduleTransaction(transaction);
        } else {
            repository.addTransaction(transaction);
        }

        return true;
    }
//-----------------------------------------------------------------------------

    private void scheduleTransaction(Transaction transaction) {
        RepeateTransactionCreationTask task = context.getBean(RepeateTransactionCreationTask.class);
        task.setTransaction(transaction);

        scheduler.schedule(task, dateUtil.getDate(transaction.getTimestamp()));
    }
}
