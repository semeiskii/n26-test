package ru.gowk.n26.domain;

import ru.gowk.n26.validation.NotZero;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 11.12.2017
 */
public class Transaction {
    @NotNull
    @NotZero
    private BigDecimal amount;
    @DecimalMin(value = "0", inclusive = false)
    private long timestamp;
//-----------------------------------------------------------------------------

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
//-----------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
