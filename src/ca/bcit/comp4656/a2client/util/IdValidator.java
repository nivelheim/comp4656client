package ca.bcit.comp4656.a2client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("IdValidator")
public class IdValidator implements Validator {
	
	private static final String INPUT_PATTERN = "[A][0][0-9]{7}";

	private Pattern pattern;
	private Matcher matcher;
	
	public IdValidator() {
		 pattern = Pattern.compile(INPUT_PATTERN);
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		matcher = pattern.matcher(arg2.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("Must start with A0 and 7 digits");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

}
