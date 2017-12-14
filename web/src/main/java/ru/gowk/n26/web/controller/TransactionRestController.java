package ru.gowk.n26.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

import javax.validation.Valid;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
@RestController
public class TransactionRestController {
    private static final ResponseEntity RESPONSE_BAD_REQUEST = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    private static final ResponseEntity RESPONSE_NO_CONTENT = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    private static final ResponseEntity RESPONSE_CREATED = ResponseEntity.status(HttpStatus.CREATED).build();

    private final TransactionService service;
//-----------------------------------------------------------------------------

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }
//-----------------------------------------------------------------------------

    @PostMapping("transactions")
    public ResponseEntity createTransaction(@RequestBody @Valid Transaction transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RESPONSE_BAD_REQUEST;
        }

        return service.createTransaction(transaction) ?
                RESPONSE_CREATED :
                RESPONSE_NO_CONTENT;
    }
}
