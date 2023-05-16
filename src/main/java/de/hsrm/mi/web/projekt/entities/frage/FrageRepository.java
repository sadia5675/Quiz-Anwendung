package de.hsrm.mi.web.projekt.entities.frage;

import org.springframework.data.jpa.repository.JpaRepository;

//Schnittstelle für die Datenbankinteraktion mit der Entität Frage
//JpaRepository erbt (Create, Read, Update, Delete) die Entität Frage benötigt
//long  gibt den Datentyp des Primärschlüssels (id) der Entität Frage an
public interface FrageRepository extends JpaRepository<Frage, Long> {
    
}
