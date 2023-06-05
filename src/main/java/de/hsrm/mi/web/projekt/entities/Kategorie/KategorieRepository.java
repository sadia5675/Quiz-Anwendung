package de.hsrm.mi.web.projekt.entities.Kategorie;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
@author Ana-Maria Adanaia
 */

public interface KategorieRepository extends JpaRepository <Kategorie, Long> {
    
    Optional<Kategorie> findByName(String name);

    <S extends Kategorie> S save(S kategorie);
}
