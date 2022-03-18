package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande,Long> {

    List<Demande> findByEnginsAffecteListIsNotNull();
    List<Demande> findByEnginsAffecteListIsNull();
    List<Demande> findAllByOrderByDateDemande();

}
