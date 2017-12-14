package ru.gowk.n26.service;

import ru.gowk.n26.domain.Statistic;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public interface StatisticService {
    /**
     * Calculates and returns actual statistic.
     *
     * @return actual statistic
     */
    Statistic getStatistic();
}
