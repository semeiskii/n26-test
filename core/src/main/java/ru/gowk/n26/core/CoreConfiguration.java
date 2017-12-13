package ru.gowk.n26.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gowk.n26.core.repository.RoundRobbinStatisticRepository;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.core.util.DateUtil;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@Configuration
@EnableConfigurationProperties(CoreProperties.class)
public class CoreConfiguration {
    @Bean
    public DateUtil dateUtil() {
        return new DateUtil();
    }

    @Bean
    public StatisticRepository roundRobbinStatisticRepository(DateUtil dateUtil, CoreProperties properties) {
        return new RoundRobbinStatisticRepository(dateUtil, properties.getStatisticPeriod());
    }
}
