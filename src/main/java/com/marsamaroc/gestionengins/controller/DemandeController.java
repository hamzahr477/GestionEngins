package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.DemandeDTO;
import com.marsamaroc.gestionengins.dto.DemandeCompletDTO;
import com.marsamaroc.gestionengins.dto.EnginAffecteeDTO;
import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/demandes")
public class DemandeController {

    @Autowired
    DetailsDemandeService detailsDemandeService;
    @Autowired
    DemandeService demandeService;
    @Autowired
    FamilleService familleService;
    @Autowired
    PostService postService;
    @Autowired
    EnginService enginService;
    @Autowired
    EnginAffecteService enginAffecteService;
    @Autowired
    ControleService controleService;
    @Autowired
    CritereService critereService;
    @Autowired
    UserService userService;
    @RequestMapping(value="/all",method= RequestMethod.GET)
    List<DemandeDTO> getAllDemande(){
        List<Demande> demandeList= demandeService.findAllDemande();
        List<DemandeDTO> demandeDTOList = new ArrayList<>();
        for(Demande demande : demandeList){
            demandeDTOList.add(new DemandeDTO(demande));
        }
        return demandeDTOList;
    }

    @RequestMapping(value="/enregistree",method= RequestMethod.GET)
    ResponseEntity<?> getDemandeEnregistree(){
        List<Demande> demandeList= demandeService.findAllDemandeEnregistree();
        List<DemandeDTO> demandeDTOList = new ArrayList<>();
        for(Demande demande : demandeList){
            demandeDTOList.add(new DemandeDTO(demande));
        }
        return new ResponseEntity<>(demandeDTOList , HttpStatus.OK);
    }
    @RequestMapping(value="/verifiee",method= RequestMethod.GET)
    ResponseEntity<?> getDemandeVerifiee(){
        List<Demande> demandeList= demandeService.findAllDemandeVerifiee();
        List<DemandeDTO> demandeDTOList = new ArrayList<>();
        for(Demande demande : demandeList){
            demandeDTOList.add(new DemandeDTO(demande));
        }
        return new ResponseEntity<>(demandeDTOList , HttpStatus.OK);
    }



    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    ResponseEntity<?> getDemande(@PathVariable("id") String id ){
        Demande demande = demandeService.getById(Long.parseLong(id));
        if(demande == null)
            return null;
        List<EnginDTO> enginDTOList = new ArrayList<>();
        for(EnginAffecte enginAffecte : demande.getEnginsAffecteList()){
            EnginDTO enginDTO= new EnginDTO(enginAffecte.getEngin()  , enginAffecte);
            enginDTOList.add(enginDTO);
            System.out.println(enginDTO.getFamille().getDateDerniereAffectation());
        }
        DemandeCompletDTO demandeCompletDTOList = new DemandeCompletDTO(
                demande,enginDTOList);
        return new ResponseEntity<>(demandeCompletDTOList , HttpStatus.OK);
    }

    @PostMapping(value="/add")
    ResponseEntity<?> addDemande(@RequestBody Demande demande) {
        Demande newDemande = demandeService.saveDamande(demande);
        return new ResponseEntity<>(new DemandeDTO(newDemande) , HttpStatus.OK);
    }

    @PostMapping(value="/reserver")
    ResponseEntity<?> reserveEnins(@RequestBody List<EnginAffecte> enginAffecteList) {
        List<EnginAffecteeDTO>  enginAffecteeDTOList = new ArrayList<>();
        for ( EnginAffecte enginAffecte :  enginAffecteList){
            enginAffecte.getEngin().setEtat(EtatEngin.sortie);
            enginAffecte.setIdDemandeEngin(enginAffecteService.saveEnginDemande(enginAffecte).getIdDemandeEngin());
            enginAffecteeDTOList.add(new EnginAffecteeDTO(enginAffecte));
            for(Controle controle : enginAffecte.getControleEngin()){
                Controle oldControle = controleService.getControlByIdCritereAndIdAffectation(controle.getCritere().getIdCritere(),enginAffecte.getIdDemandeEngin());
                controle.setId(oldControle==null ? null : oldControle.getId());
                controleService.save(controle , enginAffecte);
            }
            enginService.update(enginAffecte.getEngin());
        }
        return new ResponseEntity<>(enginAffecteeDTOList , HttpStatus.OK);
    }


    @PostMapping(value="/supenginaffect")
    ResponseEntity<?>  deletEnginAffect(@RequestBody EnginAffecte enginAffecte){
        enginAffecte = enginAffecteService.getById(enginAffecte.getIdDemandeEngin());
        enginAffecteService.delete(enginAffecte);
        if(enginAffecte.getEngin().getEtat() == EtatEngin.sortie) {
            enginAffecte.getEngin().setEtat(EtatEngin.disponible);
            enginService.update(enginAffecte.getEngin());
        }
        return new ResponseEntity<>(enginAffecte , HttpStatus.OK);
    }

    @PostMapping(value="delete/{numBCI}")
    ResponseEntity<?> deletDemande(@PathVariable("numBCI") Long numBCI){
        Demande demande = demandeService.getById(numBCI);
        if(demande.getEnginsAffecteList().isEmpty()){
            demandeService.deletDemande(demande);
            return new ResponseEntity<>("deleted" , HttpStatus.OK);
        }
        return new ResponseEntity<>("error" , HttpStatus.EXPECTATION_FAILED);
    }

    @PostMapping(value="/affecter")
    ResponseEntity<?> affecterEngins(@RequestBody List<EnginAffecte> enginAffecteList) {

        //Delete if is not exist
        List<EnginAffecte> enginAffecteListOld = demandeService.getById(enginAffecteList.get(0).getDemande().getNumBCI()).getEnginsAffecteList();
        for(EnginAffecte enginAffecte : enginAffecteListOld){
            if(!enginAffecteList.contains(enginAffecte))
                enginAffecteService.delete(enginAffecte);
        }
        //Insert
        List<EnginAffecteeDTO>  enginAffecteeDTOList = new ArrayList<>();
        for (EnginAffecte enginAffecte : enginAffecteList) {
            enginAffecte.setDateAffectation(new Date());
            enginAffecte.setEtat(EtatAffectation.reserve);
            enginAffecte.setEngin(enginService.getById(enginAffecte.getEngin().getCodeEngin()));
            enginAffecte.getEngin().setEtat(EtatEngin.sortie);
            enginAffecteeDTOList.add(new EnginAffecteeDTO(enginAffecteService.saveEnginDemande(enginAffecte)));
            enginService.update(enginAffecte.getEngin());
        }
        return new ResponseEntity<>(enginAffecteeDTOList , HttpStatus.OK);

    }

    @PostMapping(value="/controler")
    ResponseEntity<?> controleEnginsAffecte(@RequestBody List<EnginAffecte> enginAffecteList){
        for(EnginAffecte newEnginAffecte : enginAffecteList) {
            EnginAffecte enginAffecte = enginAffecteService.getById(newEnginAffecte.getIdDemandeEngin());
            if(enginAffecte.getControleEngin().isEmpty()){
                //Insert
                for (Controle controle : newEnginAffecte.getControleEngin()){
                    controleService.save(controle,newEnginAffecte);
                    }
                enginAffecte.setEtat(EtatAffectation.reserve);
                }else{
                //Modify
                Controle newControle;
                for (Controle controle : newEnginAffecte.getControleEngin()){
                    newControle = controleService.getControlByIdCritereAndIdAffectation(controle.getCritere().getIdCritere(),newEnginAffecte.getIdDemandeEngin());
                    controle.setId(newControle.getId());
                    controleService.save(controle,newEnginAffecte);
                }
            }
            enginAffecteService.saveEnginDemande(enginAffecte);
        }

        return new ResponseEntity<>("Done" , HttpStatus.OK);
    }

    @PostMapping(value="/submit")
    ResponseEntity<?> submitDemandeSortie(@RequestBody EnginAffecte enginAffecte){
        EnginAffecte enginAffecteOld = enginAffecteService.getById(enginAffecte.getIdDemandeEngin());
        enginAffecteOld.sync(enginAffecte);
        if(enginAffecte.getConducteur() != null && enginAffecte.getResponsableAffectation()!=null){
            enginAffecte.getResponsableAffectation().setType("Responsable");
            enginAffecte.getResponsableAffectation().setEnable('N');
            enginAffecte.getConducteur().setEntite(enginAffecteOld.getDemande().getPost().getEntite());
            enginAffecte.getConducteur().setEnable('N');
            enginAffecte.getConducteur().setType("Conducteur");
            User responsable = userService.saveUserIfNotExist(enginAffecte.getResponsableAffectation());
            User conducteur = userService.saveUserIfNotExist(enginAffecte.getConducteur());
            enginAffecteOld.setResponsableAffectation(responsable);
            enginAffecteOld.setConducteur(conducteur);
        }
        if (enginAffecteOld.getControleEngin().get(0).getEtatEntree() != 0){
            enginAffecteOld.setEtat(EtatAffectation.execute);
            enginAffecteOld.getEngin().setEtat(EtatEngin.disponible);
            enginAffecteOld.setDateEntree(new Date());
        }
        else{
            enginAffecteOld.setEtat(EtatAffectation.enexecution);
            enginAffecteOld.setDateSortie(new Date());
        }
        enginAffecteService.saveEnginDemande(enginAffecteOld);
        controleService.saveAll(enginAffecteOld.getControleEngin());
        enginService.update(enginAffecteOld.getEngin());
        return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecteOld) , HttpStatus.OK);
    }


    @RequestMapping(value="/engin/{idEngin}",method= RequestMethod.GET)
    ResponseEntity<?> ElisteEnginsEntree(@PathVariable(name = "idEngin") String idEngin){
        Engin engin = enginService.getById(idEngin);
        return new ResponseEntity<>(new DemandeCompletDTO(engin.getDerniereAffectation().getDemande(), Arrays.asList(new EnginDTO(engin,engin.getDerniereAffectation()))) , HttpStatus.OK);
    }

}
