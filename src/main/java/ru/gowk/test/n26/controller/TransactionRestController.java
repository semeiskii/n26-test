package ru.gowk.test.n26.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gowk.test.n26.domain.Transaction;
import ru.gowk.test.n26.service.TransactionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RestController
public class TransactionRestController {
    private static final ResponseEntity RESPONSE_NO_CONTENT = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    private static final ResponseEntity RESPONSE_CREATED = ResponseEntity.status(HttpStatus.CREATED).build();

    private TransactionService service;
//-----------------------------------------------------------------------------

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }
//-----------------------------------------------------------------------------

    @PostMapping("transactions")
    public ResponseEntity createTransaction(@Valid Transaction transaction, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return RESPONSE_NO_CONTENT;
        }

        service.createTransaction(transaction);
        return RESPONSE_CREATED;
    }
}
