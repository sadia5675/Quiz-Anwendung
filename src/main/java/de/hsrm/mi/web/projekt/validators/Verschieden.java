package de.hsrm.mi.web.projekt.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=VerschiedenValidator.class)

public @interface Verschieden {
    String message() default "{frageformular.fehler.falschantworten.duplikate}";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    
}