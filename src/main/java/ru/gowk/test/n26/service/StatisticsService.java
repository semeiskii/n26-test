package ru.gowk.test.n26.service;

import org.springframework.stereotype.Service;
import ru.gowk.test.n26.domain.Statistic;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public interface StatisticsService {
    Statistic getStatistic();
}
