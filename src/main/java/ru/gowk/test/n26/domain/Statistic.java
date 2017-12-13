package ru.gowk.test.n26.domain;

import ru.gowk.test.n26.repository.DomainStat;

import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class Statistic {
    private BigDecimal sum;
    private double avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;
//-----------------------------------------------------------------------------

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
//-----------------------------------------------------------------------------

    public void copy(Statistic statistic) {
        sum = statistic.sum;
        avg = statistic.avg;
        max = statistic.max;
        min = statistic.min;
        count += statistic.count;
    }

    public void accumulate(DomainStat statistic) {
        sum = sum.add(statistic.getSum());
        if (max.compareTo(statistic.getMax()) < 0) {
            max = statistic.getMax();
        }
        if (min.compareTo(statistic.getMin()) > 0) {
            min = statistic.getMin();
        }
        count += statistic.getCount();
    }

    public void calculateAverage() {
        avg = count == 0 ?
                0. :
                sum.doubleValue() / count;
    }
//-----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Statistic{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
