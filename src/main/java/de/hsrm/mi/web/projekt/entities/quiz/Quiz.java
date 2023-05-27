/**
 * @author Sadia Miah
 */

package de.hsrm.mi.web.projekt.entities.quiz;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.web.projekt.entities.frage.Frage;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
public class Quiz {
    @Id 
    @GeneratedValue 
    private long id;
    
    @Version
    private long version;
    
    @NotNull
    private String titel;

    @ManyToMany
    private List<Frage> fragen = new ArrayList<>();
    

    public Quiz() {};

    public Quiz(String titel) {
        this.titel = titel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<Frage> getFragen() {
        return fragen;
    }

    public void setFragen(List<Frage> ausgewaehlteFragen) {
        this.fragen = ausgewaehlteFragen;
    }

}
