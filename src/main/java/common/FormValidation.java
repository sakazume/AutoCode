package common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class FormValidation {

	public Map<String, String> valedata() {
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();

		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<FormValidation>> violations =
				validator.validate(this);

		Map<String, String> errorValidatorMap = new HashMap<>();
		for (ConstraintViolation<? extends FormValidation> violation : violations) {
			errorValidatorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return errorValidatorMap;
	}
}
