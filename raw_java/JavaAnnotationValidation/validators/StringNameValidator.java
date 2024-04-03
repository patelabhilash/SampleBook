

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.customannotation.ValidStringName;

public class StringNameValidator implements ConstraintValidator<ValidStringName, String> {

	private String namePattern;

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		boolean flag = true;
		if (name != null) {
			Pattern pattern = Pattern.compile(namePattern);
			Matcher matcher = pattern.matcher(name);
			flag = matcher.matches();
			if (!flag)
				// logger.debug("Invalid Application Name Pattern");
				return flag;
		} else {
			// logger.debug("Null - Application Name Pattern");
			return false;
		}
		return flag;
	}

	@Override
	public void initialize(ValidStringName constraintAnnotation) {
		this.namePattern = "^[a-zA-Z]+([a-zA-Z0-9\\s_-]+)?$";

	}
}
