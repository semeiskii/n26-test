package ru.gowk.n26.core.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.gowk.n26.domain.Transaction;
import ru.gowk.n26.service.TransactionService;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RepeateTransactionCreationTask implements Runnable {
    private final TransactionService service;

    private Transaction transaction;
//-----------------------------------------------------------------------------

    public RepeateTransactionCreationTask(TransactionService service) {
        this.service = service;
    }
//-----------------------------------------------------------------------------

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
//-----------------------------------------------------------------------------

    @Override
    public void run() {
        service.createTransaction(transaction);
    }
}
