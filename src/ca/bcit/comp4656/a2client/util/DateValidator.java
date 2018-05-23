package ca.bcit.comp4656.a2client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("DateValidator")
public class DateValidator implements Validator {

	private static final String INPUT_PATTERN = "([0-9]{4})\\/(0[1-9]|1[012])\\/(0[1-9]|[12][0-9]|3[01])";

	private Pattern pattern;
	private Matcher matcher;
	
	public DateValidator() {
		 pattern = Pattern.compile(INPUT_PATTERN);
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		matcher = pattern.matcher(arg2.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("Must be in a form yyyy/mm/dd");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

}
