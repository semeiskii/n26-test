package ru.gowk.n26.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must not be equal to zero.
 * Accepts {@link java.math.BigDecimal} type.
 *
 * @author Vyacheslav Gorbatykh
 * @since 13.12.2017
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {BigDecimalNotZeroValidator.class})
public @interface NotZero {

    String message() default "{ru.gowk.n26.validation.NotZero.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

