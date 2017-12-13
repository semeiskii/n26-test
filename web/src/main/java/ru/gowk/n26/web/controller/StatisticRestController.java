package ru.gowk.n26.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gowk.n26.service.StatisticService;
import ru.gowk.n26.web.domain.RestStatistic;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RestController
public class StatisticRestController {
    private final StatisticService service;
//-----------------------------------------------------------------------------

    public StatisticRestController(StatisticService service) {
        this.service = service;
    }
//-----------------------------------------------------------------------------

    @GetMapping("statistics")
    public RestStatistic getStatistics() {
        RestStatistic statistic = new RestStatistic();

        statistic.copy(service.getStatistic());

        return statistic;
    }
}
