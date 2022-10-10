package dev.danilosales.bank.validations.operationtype;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OperationTypeExistsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationTypeExists {
    String message() default "Operation Type does not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
