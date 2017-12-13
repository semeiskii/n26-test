package ru.gowk.n26.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRestControllerSmokeTest {
    @Autowired
    private TransactionRestController transactionRestController;
//-----------------------------------------------------------------------------

    @Test
    public void contexLoads() throws Exception {
        assertThat(transactionRestController).isNotNull();
    }
}