package de.hsrm.mi.web.projekt.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy=VerschiedenValidator.class)
public @interface Verschieden {

    String message() default "Liste darf keine Duplikate enthalten";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    
}