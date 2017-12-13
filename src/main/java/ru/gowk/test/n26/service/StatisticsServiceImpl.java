package ru.gowk.test.n26.service;

import org.springframework.stereotype.Service;
import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.repository.StatisticRepository;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private StatisticRepository repository;
//-----------------------------------------------------------------------------

    public StatisticsServiceImpl(StatisticRepository repository) {
        this.repository = repository;
    }
//-----------------------------------------------------------------------------

    @Override
    public Statistic getStatistic() {
        return repository.getStatistic();
    }
}
