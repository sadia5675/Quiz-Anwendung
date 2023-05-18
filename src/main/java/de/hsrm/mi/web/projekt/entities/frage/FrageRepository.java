package de.hsrm.mi.web.projekt.entities.frage;

import org.springframework.data.jpa.repository.JpaRepository;

//Schnittstelle für die Datenbankinteraktion mit der Entität Frage
//stellt Methoden bereit, um Datenbankabfragen durchzuführen, Datensätze zu erstellen, zu aktualisieren, zu löschen...
//long  gibt den Datentyp des Primärschlüssels (id) der Entität Frage an
public interface FrageRepository extends JpaRepository<Frage, Long> {
    
}
