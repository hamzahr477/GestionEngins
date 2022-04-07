package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Utilisateur;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.TypeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestResource(path = "users")
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByMatricule(String matricule);
    Optional<List<Utilisateur>> findAllByType(TypeUser type);


    @Query(value="SELECT DISTINCT u.* from utilisateur u \n" +
            "LEFT JOIN engin_affecte ea ON u.id = ea.conducteur\n" +
            "LEFT JOIN demande dm ON ea.id_demande=dm.numbci\n" +
            "WHERE  ( ea.date_sortie = (select max(ea_.date_sortie) from engin_affecte ea_ GROUP by ea_.conducteur HAVING ea_.conducteur = ea.conducteur)\n" +
            "and\n" +
            "(ea.date_entree is not null or CAST( ea.date_sortie AS DATE) + interval dm.shift*8 hour < CURRENT_TIMESTAMP ))\n" +
            "or(ea.id_demande_engin is null)\n" +
            ";", nativeQuery = true)
    Optional<List<Utilisateur>> getAllDispConducteur();
}
