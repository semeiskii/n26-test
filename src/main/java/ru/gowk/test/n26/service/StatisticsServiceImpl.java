package ru.gowk.test.n26.service;

import org.springframework.stereotype.Service;
import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.repository.StatisticRepository;

import java.util.List;
import java.util.Objects;

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
        Statistic result = new Statistic();

        List<Statistic> statisticList = repository.getPreliminaryStatistic();
        statisticList.stream()
                .filter(Objects::nonNull)
                .forEach(statistic -> {
                    if (result.getCount() == 0) {
                        result.copy(statistic);
                    } else {
                        result.accumulate(statistic);
                    }
                });

        result.calculateAverage();

        return result;
    }
}
