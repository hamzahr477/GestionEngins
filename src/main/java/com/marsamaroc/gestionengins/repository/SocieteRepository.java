package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
public interface SocieteRepository extends JpaRepository<Societe,Long> {

}
