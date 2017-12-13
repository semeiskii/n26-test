package ru.gowk.n26.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.gowk.n26.core.repository.RoundRobbinStatisticRepository;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.core.service.TransactionServiceImpl;
import ru.gowk.n26.core.util.DateUtil;
import ru.gowk.n26.service.TransactionService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Configuration
@EnableConfigurationProperties(CoreProperties.class)
@EnableScheduling
public class CoreConfiguration {
    @Bean
    public DateUtil dateUtil() {
        return new DateUtil();
    }

    @Bean
    public StatisticRepository roundRobbinStatisticRepository(DateUtil dateUtil, CoreProperties properties) {
        return new RoundRobbinStatisticRepository(dateUtil, properties.getStatisticPeriod());
    }

    @Bean
    public TransactionService transactionServiceImpl(StatisticRepository repository, DateUtil dateUtil,
                                                     ApplicationContext context, TaskScheduler scheduler,
                                                     CoreProperties properties) {
        return new TransactionServiceImpl(repository, dateUtil, context, scheduler, properties.getStatisticPeriod());
    }

    @Bean
    public TaskScheduler threadPoolTaskScheduler(CoreProperties properties) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(properties.getDalayedTransactionsPoolSize());
        return scheduler;
    }
}
