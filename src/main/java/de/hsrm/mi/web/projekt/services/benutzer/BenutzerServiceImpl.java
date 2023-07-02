package de.hsrm.mi.web.projekt.services.benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerServiceImpl implements BenutzerService {


    private final BenutzerRepository benutzerRepository;

     @Autowired
    public BenutzerServiceImpl(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }






    // Um einen neuen Benutzer anzulegen
    @Override
    public Benutzer erstelleBenutzer(String benutzername, String losung) {
       Benutzer benutzer = new Benutzer(benutzername, losung);
       return benutzerRepository.save(benutzer);
    }


    // Zu checken, ob ein gegebener Benutzername schon vergeben ist
    @Override
    public boolean istBenutzernameVerfuegbar(String benutzername) {
        if (benutzerRepository.existsById(benutzername)) {
        return false; 
      } else {
        return true;}
    }


    // Ein Benutzer-Objekt nach Angabe des Benutzernamens aus der DB zu holen
    @Override
    public Benutzer findeBenutzerNachBenutzername(String benutzername) {
       return benutzerRepository.findById(benutzername).orElse(null);
    }
    
}
