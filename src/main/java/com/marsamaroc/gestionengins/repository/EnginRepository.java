package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Repository
public interface EnginRepository extends JpaRepository<Engin,String> {


    @Query("select distinct e from Engin e , EnginAffecte ea" +
            " where e.codeEngin = ea.engin.codeEngin "+
            "and ea.etat = '"+ EtatAffectation.reserve_value+"'")
    List<Engin> findAllEnginAffecteAndPreSortie();



    @Query("select distinct e from Engin e , EnginAffecte ea" +
            " where e.codeEngin = ea.engin.codeEngin "+
            "and ea.etat = '"+ EtatAffectation.enexecution_value+"'")
    List<Engin> findAllEnginSortie();
    
    @Query("select e from Engin e"+
            " where e.etat ='"+ EtatEngin.disponible_value+
            "' and e.famille.idFamille = :#{#idFamille} " +
            "and e.disponibiliteParck='"+ DisponibiliteEnginParck.disponible_value+"'")
    List<Engin> findAllEnginEntreeByFamille(@Param("idFamille") Long famille );

    Engin findByCodeEngin(String codeEngin);
}
