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

import static org.assertj.core.api.Assertions.assertThat;
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

    private String url;
//-----------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:" + port + "/transactions";
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
    }

    @Test
    public void shouldNotCreateTransactionWithInvalidAmount() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(10);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldNotCreateTransactionWithInvalidTimestamp() throws Exception {
        Transaction transaction = transaction(12.34, 0);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, transaction, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void shouldRejectStringAmount() throws Exception {
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, new AmountAsStringTransaction(), null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectStringTimestamp() throws Exception {
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
                url, new TimestampAsStringTransaction(), null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldRejectGet() throws Exception {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                url, null);
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