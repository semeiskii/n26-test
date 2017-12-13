package ru.gowk.n26.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@ConfigurationProperties("core")
public class CoreProperties {
    private int statisticPeriod;
    private int dalayedTransactionsPoolSize = 3;
//-----------------------------------------------------------------------------

    public int getStatisticPeriod() {
        return statisticPeriod;
    }

    public void setStatisticPeriod(int statisticPeriod) {
        this.statisticPeriod = statisticPeriod;
    }

    public int getDalayedTransactionsPoolSize() {
        return dalayedTransactionsPoolSize;
    }

    public void setDalayedTransactionsPoolSize(int dalayedTransactionsPoolSize) {
        this.dalayedTransactionsPoolSize = dalayedTransactionsPoolSize;
    }
}
