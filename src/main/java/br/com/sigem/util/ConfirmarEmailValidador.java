package br.com.sigem.util;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmarEmailValidador implements ConstraintValidator<ConfirmarEmail, Object> {
	 
    private String baseField;
    private String matchField;
 
    @Override
    public void initialize(ConfirmarEmail constraint) {
        baseField = constraint.baseField();
        matchField = constraint.matchField();
    }
 
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
    	try {
    	      Object firstObj = getFieldValue(object, baseField);
              Object secondObj = getFieldValue(object, matchField);

              context.disableDefaultConstraintViolation();
              
    	      boolean isValid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

    	      if (!isValid) {
    	        context.disableDefaultConstraintViolation();
    	        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(matchField).addConstraintViolation();
    	      }

    	      return isValid;
    	    }
    	    catch (final Exception ignore) {
    	      // ignore
    	    }
    	    return true;
    	  }
    	
 
    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field passwordField = clazz.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }
 
}
