
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.validators.EmailNameValidator;

@Documented
@Constraint(validatedBy = EmailNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailName {

	String message() default "{Invalid Email received.}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
