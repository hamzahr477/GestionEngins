package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@RestResource
public interface EnginAffecteRepository extends JpaRepository<EnginAffecte,Long> {

    List<EnginAffecte> findAllByEngin_CodeEngin(String codeEngin);
    EnginAffecte findByEnginCodeEnginAndDemandeNumBCI(String codeEngin , Long numBCI);

    @Query(value = "select ea from EnginAffecte ea " +
            "LEFT JOIN Panne p ON (p.currentDemande.numBCI = ea.demande.numBCI and p.engin.codeEngin=ea.engin.codeEngin ) " +
            "where (:codeEngins is null or  ea.engin.codeEngin IN (:codeEngins)) " +
            "and ( :type is null or ea.etat = :type) " +
            "and ( :numBCI is null or ea.demande.numBCI = :numBCI) " +
            "and ( :familles is null or ea.engin.famille.idFamille IN (:familles)) " +
            "and ( :conducteurs is null or ea.conducteur_sortie.matricule IN (:conducteurs)) " +
            "and ( :responsables is null or ea.responsableAffectation_sortie.matricule IN (:responsables)) " +
            "and ( :dateMinSortie is null or ea.dateSortie >= :dateMinSortie) " +
            "and ( :dateMaxSortie is null or ea.dateSortie >= :dateMaxSortie) " +
            "and ( :dateMaxEntree is null or ea.dateEntree <= :dateMaxEntree) " +
            "and ( :dateMinEntree is null or ea.dateEntree >= :dateMinEntree) " +
            "and ( :dateMinAffectation is null or ea.dateAffectation >= :dateMinAffectation) " +
            "and ( :dateMaxAffectation is null or ea.dateAffectation <= :dateMaxAffectation) " +
            " and ea.etat <> '"+EtatAffectation.reserve_value+"'")
    Page<EnginAffecte> search(EtatAffectation type,
                              List<Long> familles,
                              List<String> codeEngins,
                              Long numBCI,
                              List<String> conducteurs,
                              List<String> responsables,
                              Date dateMaxAffectation,
                              Date dateMinAffectation,
                              Date dateMaxEntree,
                              Date dateMinEntree,
                              Date dateMaxSortie,
                              Date dateMinSortie,
                              Pageable pageable);
}
