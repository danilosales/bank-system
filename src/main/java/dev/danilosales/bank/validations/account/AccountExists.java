package dev.danilosales.bank.validations.account;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountExistsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountExists {
    String message() default "Account does not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
