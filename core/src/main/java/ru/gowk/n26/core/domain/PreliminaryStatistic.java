package ru.gowk.n26.core.domain;

import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.domain.Transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class PreliminaryStatistic extends Statistic {
    private long domain;
//-----------------------------------------------------------------------------

    public PreliminaryStatistic() {
    }

    public PreliminaryStatistic(long domain, Transaction transaction) {
        this.domain = domain;

        setSum(transaction.getAmount());
        setMax(transaction.getAmount());
        setMin(transaction.getAmount());
        setCount(1);
    }

    public long getDomain() {
        return domain;
    }

    public void setDomain(long domain) {
        this.domain = domain;
    }
//-----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "PreliminaryStatistic{" +
                "domain=" + domain +
                "} " + super.toString();
    }
}
