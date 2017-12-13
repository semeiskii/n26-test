package ru.gowk.n26.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.gowk.n26.util.TestUtil.big;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
public class StatisticTest {
    @Test
    public void initShouldFlushTheState() throws Exception {
        Statistic statistic = new Statistic();
        statistic.setSum(big(12.34));
        statistic.setMax(big(56.78));
        statistic.setMin(big(09.12));
        statistic.setCount(3);

        assertThat(statistic.getSum()).isNotNull().isEqualTo(big(12.34));
        assertThat(statistic.getMax()).isNotNull().isEqualTo(big(56.78));
        assertThat(statistic.getMin()).isNotNull().isEqualTo(big(09.12));
        assertThat(statistic.getCount()).isEqualTo(3);

        statistic.init();

        assertThat(statistic.getSum()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMax()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMin()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getCount()).isEqualTo(0);
    }

    @Test
    public void copyShouldCloneTheState() throws Exception {
        Statistic statistic1 = new Statistic();
        statistic1.setSum(big(12.34));
        statistic1.setMax(big(56.78));
        statistic1.setMin(big(09.12));
        statistic1.setCount(3);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(12.34));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(56.78));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(09.12));
        assertThat(statistic1.getCount()).isEqualTo(3);

        Statistic statistic2 = new Statistic();
        statistic2.setSum(big(34.56));
        statistic2.setMax(big(78.90));
        statistic2.setMin(big(43.21));
        statistic2.setCount(8);

        statistic1.copy(statistic2);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(34.56));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(78.90));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(43.21));
        assertThat(statistic1.getCount()).isEqualTo(8);
    }

    @Test
    public void accumulateShouldChangeValues() throws Exception {
        Statistic statistic1 = new Statistic();
        statistic1.setSum(big(12.34));
        statistic1.setMax(big(56.78));
        statistic1.setMin(big(09.12));
        statistic1.setCount(3);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(12.34));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(56.78));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(09.12));
        assertThat(statistic1.getCount()).isEqualTo(3);

        Statistic statistic2 = new Statistic();
        statistic2.setSum(big(34.56));
        statistic2.setMax(big(78.90));
        statistic2.setMin(big(43.21));
        statistic2.setCount(8);

        statistic1.accumulate(statistic2);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(12.34).add(big(34.56)));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(78.90));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(09.12));
        assertThat(statistic1.getCount()).isEqualTo(11);
    }
}