package ru.gowk.n26.web.domain;

import ru.gowk.n26.domain.Statistic;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class RestStatistic extends Statistic {
    private double avg;
//-----------------------------------------------------------------------------

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
//-----------------------------------------------------------------------------

    @Override
    public void init() {
        super.init();
        calculateAvg();
    }

    @Override
    public void copy(Statistic statistic) {
        super.copy(statistic);
        calculateAvg();
    }

    @Override
    public void accumulate(Statistic statistic) {
        super.accumulate(statistic);
        calculateAvg();
    }

    public void calculateAvg() {
        avg = getCount() == 0 ?
                0 :
                getSum().doubleValue() / getCount();
    }
//-----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "RestStatistic{" +
                "avg=" + avg +
                "} " + super.toString();
    }
}
