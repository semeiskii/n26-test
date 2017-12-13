package ru.gowk.n26.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.web.domain.RestStatistic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.gowk.n26.util.TestUtil.big;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestConfiguration configuration;

    private String url;
//-----------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:" + port + "/statistics";
    }
//-----------------------------------------------------------------------------

    @Test
    public void shouldReturnEmptyStatistic() throws Exception {
        RestStatistic statistic = restTemplate.getForObject(
                url, RestStatistic.class);
        assertThat(statistic).isNotNull();
        assertThat(statistic.getSum()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMax()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getMin()).isNotNull().isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.getCount()).isEqualTo(0);
        assertThat(statistic.getAvg()).isEqualTo(0.);
    }

    @Test
    public void shouldReturnNotEmptyStatistic() throws Exception {
        Statistic statistic = new Statistic();
        statistic.setSum(big(12.34));
        statistic.setMax(big(56.78));
        statistic.setMin(big(90.12));
        statistic.setCount(4);

        given(configuration.statisticService.getStatistic()).willReturn(statistic);

        RestStatistic result = restTemplate.getForObject(
                url, RestStatistic.class);

        assertThat(result).isNotNull();
        assertThat(result.getSum()).isNotNull().isEqualTo(big(12.34));
        assertThat(result.getMax()).isNotNull().isEqualTo(big(56.78));
        assertThat(result.getMin()).isNotNull().isEqualTo(big(90.12));
        assertThat(result.getCount()).isEqualTo(4);
        assertThat(result.getAvg()).isEqualTo(big(12.34).doubleValue() / 4);
    }

    @Test
    public void shouldReturnOnlySpecificFields() throws Exception {
        given(configuration.statisticService.getStatistic()).willReturn(new Statistic());

        Map result = restTemplate.getForObject(
                url, HashMap.class);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(5);
        assertThat(result.get("sum")).isNotNull().isEqualTo(0);
        assertThat(result.get("max")).isNotNull().isEqualTo(0);
        assertThat(result.get("min")).isNotNull().isEqualTo(0);
        assertThat(result.get("count")).isNotNull().isEqualTo(0);
        assertThat(result.get("avg")).isNotNull().isEqualTo(0.);
    }

    @Test
    public void shouldRejectPost() throws Exception {
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, "", null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
//-----------------------------------------------------------------------------

    public static class AmountAsStringTransaction {
        public String amount = "a";
        public long timestamp = 10;
    }

    public static class TimestampAsStringTransaction {
        public double amount = 0.1;
        public String timestamp = "O10";
    }
}