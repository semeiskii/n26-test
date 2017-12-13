package ru.gowk.n26.core.service;

import org.springframework.stereotype.Service;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.service.StatisticService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Service
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository repository;
//-----------------------------------------------------------------------------

    public StatisticServiceImpl(StatisticRepository repository) {
        this.repository = repository;
    }
//-----------------------------------------------------------------------------

    @Override
    public Statistic getStatistic() {
        return repository.getStatistic();
    }
}
