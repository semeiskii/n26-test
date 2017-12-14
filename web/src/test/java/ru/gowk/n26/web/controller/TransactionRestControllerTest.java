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
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.gowk.n26.util.TestUtil.transaction;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TransactionService service;

    private String url;
//-----------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:" + port + "/transactions";

        given(service.createTransaction(any())).willReturn(true);
    }
//-----------------------------------------------------------------------------

    @Test
    public void shouldCreateValidTransaction() throws Exception {
        Transaction transaction = transaction(12.34, 10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNull();

        verify(service, times(1)).createTransaction(any());
    }

    @Test
    public void shouldCreateTransactionWithNegativeAmount() throws Exception {
        Transaction transaction = transaction(-12.34, 10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNull();

        verify(service, times(1)).createTransaction(any());
    }

    @Test
    public void shouldNotCreateTransactionWithOldTimestamp() throws Exception {
        given(service.createTransaction(any())).willReturn(false);

        Transaction transaction = transaction(12.34, 10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();

        verify(service, times(1)).createTransaction(any());
    }

    @Test
    public void shouldRejectTransactionWithZeroAmount() throws Exception {
        Transaction transaction = transaction(0, 10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldRejectGet() throws Exception {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                url, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    public void shouldRejectCharAtAmount() throws Exception {
        TestTransaction transaction = new TestTransaction("a", "10");

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectCharAtTimestamp() throws Exception {
        TestTransaction transaction = new TestTransaction("0.1", "O10");

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectEmptyAmount() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldRejectZeroAmount() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.ZERO);
        transaction.setTimestamp(10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldRejectZeroTimestamp() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldRejectNegativeTimestamp() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        transaction.setTimestamp(-10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNull();
    }
//-----------------------------------------------------------------------------

    public static class TestTransaction {
        public final String amount;
        public final String timestamp;

        public TestTransaction(String amount, String timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }
    }
}