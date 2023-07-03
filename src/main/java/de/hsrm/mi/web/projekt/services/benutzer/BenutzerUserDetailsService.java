package de.hsrm.mi.web.projekt.services.benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerUserDetailsService implements UserDetailsService {

    private BenutzerRepository benutzerRepository;

    @Autowired
    public BenutzerUserDetailsService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Schritt 1: Benutzerdaten für den angegebenen Benutzernamen aus der Datenbank abrufen
        Benutzer benutzer = benutzerRepository.findByBenutzername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Schritt 2: Spring 'User'-Objekt mit den relevanten Benutzerdaten zurückgeben
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(benutzer.getLosung()) // Annahme: Losung bereits verschlüsselt in der Datenbank gespeichert
                .roles(benutzer.getRolle())
                .build();
    }


    
}
