package ru.gowk.n26.web.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.gowk.n26.util.TestUtil.big;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class RestStatisticTest {
    @Test
    public void initShouldFlushAvg() throws Exception {
        RestStatistic statistic = new RestStatistic();
        statistic.setSum(big(12.34));
        statistic.setMax(big(56.78));
        statistic.setMin(big(09.12));
        statistic.setCount(3);

        assertThat(statistic.getAvg()).isEqualTo(0);

        statistic.calculateAvg();

        assertThat(statistic.getAvg()).isEqualTo(big(12.34).doubleValue() / 3);

        statistic.init();

        assertThat(statistic.getSum()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMax()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMin()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getCount()).isEqualTo(0);
        assertThat(statistic.getAvg()).isEqualTo(0);
    }

    @Test
    public void copyShouldRecalculateAvg() throws Exception {
        RestStatistic statistic1 = new RestStatistic();
        statistic1.setSum(big(12.34));
        statistic1.setMax(big(56.78));
        statistic1.setMin(big(09.12));
        statistic1.setCount(3);
        statistic1.calculateAvg();

        assertThat(statistic1.getAvg()).isEqualTo(big(12.34).doubleValue() / 3);

        RestStatistic statistic2 = new RestStatistic();
        statistic2.setSum(big(34.56));
        statistic2.setMax(big(78.90));
        statistic2.setMin(big(43.21));
        statistic2.setCount(2);

        statistic1.copy(statistic2);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(34.56));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(78.90));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(43.21));
        assertThat(statistic1.getCount()).isEqualTo(2);
        assertThat(statistic1.getAvg()).isEqualTo(big(34.56).doubleValue() / 2);
    }

    @Test
    public void accumulateShouldRecalculateAvg() throws Exception {
        RestStatistic statistic1 = new RestStatistic();
        statistic1.setSum(big(12.34));
        statistic1.setMax(big(56.78));
        statistic1.setMin(big(09.12));
        statistic1.setCount(3);
        statistic1.calculateAvg();

        assertThat(statistic1.getAvg()).isEqualTo(big(12.34).doubleValue() / 3);

        RestStatistic statistic2 = new RestStatistic();
        statistic2.setSum(big(34.56));
        statistic2.setMax(big(78.90));
        statistic2.setMin(big(43.21));
        statistic2.setCount(8);

        statistic1.accumulate(statistic2);

        assertThat(statistic1.getSum()).isNotNull().isEqualTo(big(12.34).add(big(34.56)));
        assertThat(statistic1.getMax()).isNotNull().isEqualTo(big(78.90));
        assertThat(statistic1.getMin()).isNotNull().isEqualTo(big(09.12));
        assertThat(statistic1.getCount()).isEqualTo(11);
        assertThat(statistic1.getAvg()).isEqualTo(big(12.34).add(big(34.56)).doubleValue() / 11);
    }
}