package ru.gowk.n26.domain;

import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class Statistic {
    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;
    private long count;
//-----------------------------------------------------------------------------

    public Statistic() {
        init();
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
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

    public void init() {
        sum = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = BigDecimal.ZERO;
        count = 0;
    }

    public void copy(Statistic statistic) {
        sum = statistic.sum;
        max = statistic.max;
        min = statistic.min;
        count = statistic.count;
    }

    public void accumulate(Statistic statistic) {
        sum = sum.add(statistic.sum);
        if (count == 0 || max.compareTo(statistic.max) < 0) {
            max = statistic.max;
        }
        if (count == 0 || min.compareTo(statistic.min) > 0) {
            min = statistic.min;
        }
        count += statistic.getCount();
    }
//-----------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;

        Statistic statistic = (Statistic) o;

        if (count != statistic.count) return false;
        if (!sum.equals(statistic.sum)) return false;
        if (!max.equals(statistic.max)) return false;
        return min.equals(statistic.min);
    }

    @Override
    public int hashCode() {
        int result = sum.hashCode();
        result = 31 * result + max.hashCode();
        result = 31 * result + min.hashCode();
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
