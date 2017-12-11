package ru.gowk.test.n26.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gowk.test.n26.domain.Statistic;
import ru.gowk.test.n26.service.StatisticsService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RestController
public class StatisticRestController {
    private StatisticsService service;
//-----------------------------------------------------------------------------

    public StatisticRestController(StatisticsService service) {
        this.service = service;
    }
//-----------------------------------------------------------------------------

    @GetMapping("statistics")
    public Statistic getStatistics() {
        return service.getStatistic();
    }
}
