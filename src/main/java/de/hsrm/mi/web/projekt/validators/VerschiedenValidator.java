package de.hsrm.mi.web.projekt.validators;
import java.util.List;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VerschiedenValidator implements ConstraintValidator<Verschieden, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }

    

}
