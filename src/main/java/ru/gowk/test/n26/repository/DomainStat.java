package ru.gowk.test.n26.repository;

import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 12.12.2017
 */
public class DomainStat {
    private long domain;
    private BigDecimal sum;
    private BigDecimal min;
    private BigDecimal max;
    private long count;
//-----------------------------------------------------------------------------

    public DomainStat() {
        clear();
    }

    public long getDomain() {
        return domain;
    }

    public void setDomain(long domain) {
        this.domain = domain;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
//-----------------------------------------------------------------------------

    public void accumulate(BigDecimal amount) {
        sum = sum.add(amount);
        if (count == 0 || max.compareTo(amount) < 0) {
            max = amount;
        }
        if (count == 0 || min.compareTo(amount) > 0) {
            min = amount;
        }
        count++;
    }

    public void clear() {
        domain = 0;
        sum = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = BigDecimal.ZERO;
        count = 0;
    }
//-----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "DomainStat{" +
                "domain=" + domain +
                ", sum=" + sum +
                ", min=" + min +
                ", max=" + max +
                ", count=" + count +
                '}';
    }
}
