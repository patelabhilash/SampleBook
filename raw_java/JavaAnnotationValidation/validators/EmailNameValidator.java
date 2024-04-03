

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.customannotation.ValidEmailName;

public class EmailNameValidator implements ConstraintValidator<ValidEmailName, String> {

	private String namePattern;

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		boolean flag = false;
		if (name != null) {
			Pattern pattern = Pattern.compile(namePattern);
			Matcher matcher = pattern.matcher(name);
			return matcher.matches();
		} 
		return flag;
	}

	@Override
	public void initialize(ValidEmailName constraintAnnotation) {
		this.namePattern = "^[a-zA-Z]+([\\.\\-\\_]?[a-zA-Z0-9])+@{1}(?!gmail|GMAIL|yahoo|YAHOO|rediffmail|REDIFFMAIL)([a-zA-Z0-9])+([\\\\.][a-zA-Z]{2,}){1,2}$";

	}
}
