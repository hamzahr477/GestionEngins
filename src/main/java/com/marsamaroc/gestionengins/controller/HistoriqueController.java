package com.marsamaroc.gestionengins.controller;


import com.marsamaroc.gestionengins.dto.HistoriqueDTO;
import com.marsamaroc.gestionengins.entity.EnginAffecte;
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
                                    @RequestParam(name="search",required = false) String search , @RequestParam(name="type", required = false) EtatAffectation type) throws ResourceNotFoundException {

        Page<EnginAffecte> enginAffecteList = enginAffecteService.getAll(sortField,offset,pageSize,sortType,search,type);
        if(enginAffecteList == null || enginAffecteList.isEmpty())
            throw new ResourceNotFoundException("Historique Not Exist");
        EntityToDTO<EnginAffecte , HistoriqueDTO> entityToDTO = new EntityToDTO<>(EnginAffecte.class,HistoriqueDTO.class);
        return new ResponseEntity<>(new APIResponse<>(enginAffecteList.getContent().size(),entityToDTO.mappingToDTO(enginAffecteList)), HttpStatus.OK);
    }

}
