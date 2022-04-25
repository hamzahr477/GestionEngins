package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Demande;
import com.marsamaroc.gestionengins.entity.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeRepository extends JpaRepository<Demande,Long> {

    List<Demande> findByEnginsAffecteListIsNotNull();
    List<Demande> findByEnginsAffecteListIsNull();
    List<Demande> findAllByOrderByDateSortieDesc();

    @Query("Select d from Demande d where d.enginsAffecteList.size=0 order by d.dateSortie desc, d.shift.heureFin desc, d.dateDemande asc ")
    Optional<List<Demande>> findDemandeEnregistre();
    @Query("Select d from Demande d where d.enginsAffecteList.size<>0 order by d.dateSortie, d.shift.heureFin, d.dateDemande asc")
    Optional<List<Demande>> findDemandeVerifie();

}
