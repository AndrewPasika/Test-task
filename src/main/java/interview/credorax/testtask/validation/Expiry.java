package interview.credorax.testtask.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ExpiryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Expiry {

  String message() default "Expiry is not valid date";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
