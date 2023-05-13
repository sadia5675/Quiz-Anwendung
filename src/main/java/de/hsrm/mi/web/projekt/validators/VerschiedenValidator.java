package de.hsrm.mi.web.projekt.validators;
import java.util.List;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VerschiedenValidator implements ConstraintValidator<Verschieden, List<String>> {

    @Override
    public boolean isValid(List<String> liste, ConstraintValidatorContext context) {

        if (liste == null) {
            return true; 
        }
        for (int i = 0; i < liste.size(); i++) {
            for (int j = i + 1; j < liste.size(); j++) {
                if (liste.get(i).equalsIgnoreCase(liste.get(j))) {
                    return false; 
                }
            }
        }
        return true;
        

        /* 
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
        */

    }

}
