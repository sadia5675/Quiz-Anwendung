package de.hsrm.mi.web.projekt.services.benutzer;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;

public interface BenutzerService {
    Benutzer erstelleBenutzer(String benutzername, String losung);
    boolean istBenutzernameVerfuegbar(String benutzername);
    Benutzer findeBenutzerNachBenutzername(String benutzername);
}
