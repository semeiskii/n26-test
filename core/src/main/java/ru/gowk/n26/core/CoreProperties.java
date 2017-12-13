package ru.gowk.n26.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@ConfigurationProperties("core")
public class CoreProperties {

    private int statisticPeriod;

    public int getStatisticPeriod() {
        return statisticPeriod;
    }

    public void setStatisticPeriod(int statisticPeriod) {
        this.statisticPeriod = statisticPeriod;
    }
}
