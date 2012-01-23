package org.jgeek.website.captcha.validator;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * User: Dmitry Leontyev
 * Date: 19.01.11
 * Time: 22:24
 */
@Documented
@Constraint(validatedBy = CaptchaValidator.class)
@NotBlank
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {

    public abstract String message() default "{org.jgeek.website.captcha.validator.Captcha.message}";

    public abstract Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};

}