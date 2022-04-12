package com.marsamaroc.gestionengins.controller;


import com.marsamaroc.gestionengins.dto.HistoriqueDTO;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.service.EnginAffecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/historique")
public class HistoriqueController {
    @Autowired
    EnginAffecteService enginAffecteService;
    @GetMapping(value = "/")
    ResponseEntity<?> getHistorique() throws ResourceNotFoundException {
        List<EnginAffecte> enginAffecteList = enginAffecteService.getAll();
        if(enginAffecteList == null || enginAffecteList.isEmpty())
            throw new ResourceNotFoundException("Historique Not Exist");
        List<HistoriqueDTO> historiqueDTOList = new ArrayList<>();
        for(EnginAffecte enginAffecte : enginAffecteList)
            historiqueDTOList.add(new HistoriqueDTO(enginAffecte));
        return new ResponseEntity<>(historiqueDTOList, HttpStatus.OK);
    }

}
