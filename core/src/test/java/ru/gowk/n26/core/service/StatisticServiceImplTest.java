package ru.gowk.n26.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gowk.n26.core.CoreConfiguration;
import ru.gowk.n26.core.repository.StatisticRepository;
import ru.gowk.n26.domain.Statistic;
import ru.gowk.n26.service.StatisticService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticServiceImplTest {
    @MockBean
    private StatisticRepository repository;

    @Autowired
    private StatisticService service;
//-----------------------------------------------------------------------------

    @Test
    public void shouldUseRepositoryAddTrasaction() {
        Statistic statistic = service.getStatistic();

        Statistic repositoryStatistic = verify(repository).getStatistic();

        assertEquals(repositoryStatistic, statistic);
    }
//-----------------------------------------------------------------------------

    @SpringBootApplication
    @Import(CoreConfiguration.class)
    static class TestConfiguration {
    }
}