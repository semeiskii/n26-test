package ru.gowk.n26.web.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gowk.n26.service.StatisticService;
import ru.gowk.n26.service.TransactionService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@SpringBootApplication
public class TestConfiguration {
    @MockBean
    public TransactionService transactionService;
    @MockBean
    public StatisticService statisticService;
}
