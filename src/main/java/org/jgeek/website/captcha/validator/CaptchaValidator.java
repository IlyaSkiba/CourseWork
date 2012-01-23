package org.jgeek.website.captcha.validator;

import org.jgeek.website.captcha.CaptchaServlet;

import javax.faces.context.FacesContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: Dmitry Leontyev
 * Date: 19.01.11
 * Time: 22:33
 */
public class CaptchaValidator implements ConstraintValidator<Captcha, String> {

    @Override
    public void initialize(Captcha constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        String captchaString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(CaptchaServlet.CAPTCHA_STRING);
        if (captchaString != null) {
            result = value.equals(captchaString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(CaptchaServlet.CAPTCHA_STRING);
        }

        return result;
    }
}
