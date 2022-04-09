package com.marsamaroc.gestionengins.repository;

import com.marsamaroc.gestionengins.entity.Critere;
import com.marsamaroc.gestionengins.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface ShiftRepository extends JpaRepository<Shift,Long> {

    Optional<Shift> findAllByCodeShift(String codeShift);
}
