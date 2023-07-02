package de.hsrm.mi.web.projekt.ui.benutzer;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BenutzerFormulierernFormular {


    @NotBlank
    @Size(min = 2, max = 80)
    private String benutzername;

    @NotBlank
    @Size(min = 2, max = 80)
    private String losung;

    @NotBlank
    @Size(min = 2, max = 80)
    private String losungBestaetigen;



    //-------------------Getter & Setter-----------------------

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getLosung() {
        return losung;
    }

    public void setLosung(String losung) {
        this.losung = losung;
    }

    public String getLosungBestaetigen() {
        return losung;
    }

    public void setLosungBestaetigen(String losungBestaetigen) {
        this.losungBestaetigen = losungBestaetigen;
    }

    //----------------------------------------------------------

    public void toBenutzer (Benutzer b) {
        b.setBenutzername(this.benutzername);
        b.setLosung(this.losung);
    }

    public void fromBenutzer (Benutzer b) {
        this.benutzername = b.getBenutzername();
        this.losung = b.getLosung();
    }


    
}
