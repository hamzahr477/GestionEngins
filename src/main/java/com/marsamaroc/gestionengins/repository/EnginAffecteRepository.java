package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RestResource
public interface EnginAffecteRepository extends JpaRepository<EnginAffecte,Long> {

    List<EnginAffecte> findAllByEngin_CodeEngin(String codeEngin);
    EnginAffecte findByEnginCodeEnginAndDemandeNumBCI(String codeEngin , Long numBCI);

    @Query(value = "select ea from EnginAffecte ea" +
            " where ( " +
            " :#{#search} is null " +
            "or  ea.engin.codeEngin like %:#{#search}% " +
            "or ea.engin.nomEngin like %:#{#search}% " +
            "or ea.engin.famille.nomFamille like %:#{#search}%  )" +
            "and ( :#{#type} is null or ea.etat = :#{#type}) " +
            " and ea.etat <> '"+EtatAffectation.reserve_value+"'")
    Page<EnginAffecte> search(@Param("type") EtatAffectation type , @Param("search") String search , Pageable pageable);
}
