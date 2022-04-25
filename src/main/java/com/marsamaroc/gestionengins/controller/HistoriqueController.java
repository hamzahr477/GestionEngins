package com.marsamaroc.gestionengins.controller;


import com.marsamaroc.gestionengins.dto.HistoriqueDTO;
import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.exception.ResourceNotFoundException;
import com.marsamaroc.gestionengins.mapper.EntityToDTO;
import com.marsamaroc.gestionengins.response.APIResponse;
import com.marsamaroc.gestionengins.service.EnginAffecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/historique")
public class HistoriqueController {
    @Autowired
    EnginAffecteService enginAffecteService;
    @GetMapping(value = "/")
    ResponseEntity<?> getHistorique(@RequestParam(required = false,name="offset",defaultValue = "0") int offset,
                                    @RequestParam(required = false,name="pageSize", defaultValue = "20") int pageSize,
                                    @RequestParam(required = false,name="sortField", defaultValue ="dateModified") String sortField,
                                    @RequestParam(required = false,name="sortType",defaultValue = "DESC") String sortType,
                                    @RequestParam(name="numBCI",required = false) Long numBCI,
                                    @RequestParam(name="codeEngins",required = false) String codeEngins,
                                    @RequestParam(name="dateMinSortie",required = false) Date dateMinSortie,
                                    @RequestParam(name="dateMaxSortie",required = false) Date dateMaxSortie,
                                    @RequestParam(name="dateMinEntree",required = false) Date dateMinEntree,
                                    @RequestParam(name="dateMaxEntree",required = false) Date dateMaxEntree,
                                    @RequestParam(name="dateMinAffectation",required = false) Date dateMinAffectation,
                                    @RequestParam(name="dateMaxAffectation",required = false) Date dateMaxAffectation,
                                    @RequestParam(name="responsables",required = false) String responsables,
                                    @RequestParam(name="conducteurs",required = false) String conducteurs,
                                    @RequestParam(name="familles",required = false) String familles,
                                    @RequestParam(name="pannesField",required = false,defaultValue = "-1") String criteres,
                                    @RequestParam(name="type", required = false) EtatAffectation type) throws ResourceNotFoundException {
        Page<EnginAffecte> enginAffecteList;
        List<Long> criteres_;
        try{
            criteres_ = Arrays.stream(criteres.split(",")).map(Long::parseLong).collect(Collectors.toList());
        }catch (Exception e){
            criteres_ = Arrays.asList(-1L);
        }
        enginAffecteList = enginAffecteService.getAll(sortField,offset,pageSize,familles,conducteurs,responsables,dateMaxAffectation,dateMinAffectation,dateMaxEntree,dateMinEntree,dateMaxSortie,sortType,numBCI,codeEngins,dateMinSortie,type);
        if(enginAffecteList == null || enginAffecteList.isEmpty())
            throw new ResourceNotFoundException("Historique Not Exist");
        if(!criteres_.contains(-1L))
            for(EnginAffecte enginAffecte : enginAffecteList)  {
                for (Panne panne : enginAffecte.getEngin().getPanneList())
                    panne.filtreCriterPanne(criteres_);
            enginAffecte.getEngin().getPanneList().removeIf(panne -> panne.getDetailsPanneList().size()==0);
            }
        EntityToDTO<EnginAffecte , HistoriqueDTO> entityToDTO = new EntityToDTO<>(EnginAffecte.class,HistoriqueDTO.class);
        return new ResponseEntity<>(new APIResponse<>(enginAffecteList.getContent().size(),entityToDTO.mappingToDTO(enginAffecteList)), HttpStatus.OK);
    }

}
