
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.validators.StringNameValidator;

@Documented
@Constraint(validatedBy = StringNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStringName {

	String message() default "{Invalid format for this Field}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
