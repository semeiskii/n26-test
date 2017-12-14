package ru.gowk.n26.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
public class BigDecimalNotZeroValidator implements ConstraintValidator<NotZero, BigDecimal> {
    @Override
    public void initialize(NotZero constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value == null || value.doubleValue() != 0.;
    }
}
