package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.DemandeDTO;
import com.marsamaroc.gestionengins.dto.DemandeCompletDTO;
import com.marsamaroc.gestionengins.dto.EnginAffecteeDTO;
import com.marsamaroc.gestionengins.dto.EnginDTO;
import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.TypeUser;
import com.marsamaroc.gestionengins.exception.*;
import com.marsamaroc.gestionengins.repository.UserRepository;
import com.marsamaroc.gestionengins.service.*;
import javafx.scene.canvas.GraphicsContext;
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
    @Autowired
    UserRepository userRepository;

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
    ResponseEntity<?> getDemande(@PathVariable("id") String id ) throws ResourceNotFoundException {
        Demande demande = demandeService.findById(Long.parseLong(id)).orElseThrow(()->new ResourceNotFoundException("Demande not found for this id :: "+id));
        List<EnginDTO> enginDTOList = new ArrayList<>();
        for(EnginAffecte enginAffecte : demande.getEnginsAffecteList()){
            EnginDTO enginDTO= new EnginDTO(enginAffecte.getEngin()  , enginAffecte);
            enginDTOList.add(enginDTO);
        }
        DemandeCompletDTO demandeCompletDTOList = new DemandeCompletDTO(
                demande,enginDTOList);
        return new ResponseEntity<>(demandeCompletDTOList , HttpStatus.OK);
    }

    @PostMapping(value="/add")
    ResponseEntity<?> addDemande(@RequestBody Demande demande) {
        if(demande.getDateDemande()==null) demande.setDateDemande(new Date());

        Demande newDemande = demandeService.saveDamande(demande);

        return new ResponseEntity<>(new DemandeDTO(newDemande) , HttpStatus.OK);
    }

    @PostMapping(value="/reserver")
    ResponseEntity<?> reserveEnins(@RequestBody List<EnginAffecte> enginAffecteList) throws ResourceNotFoundException, EnginNotDisponibleException {
        List<EnginAffecteeDTO>  enginAffecteeDTOList = new ArrayList<>();
        for ( EnginAffecte enginAffecte :  enginAffecteList){
            Engin engin_ = enginService.findById(enginAffecte.getEngin().getCodeEngin()).orElseThrow(
                    ()->new ResourceNotFoundException("Engin not found for this id :: "+enginAffecte.getEngin().getCodeEngin())
            );
            EnginAffecte enginAffOld = enginAffecteService.getByEnginAndDemande(enginAffecte.getEngin(),enginAffecte.getDemande());
            if(enginAffOld == null ){
                if(engin_.getEtat() == EtatEngin.disponible && engin_.getDisponibiliteParck() == DisponibiliteEnginParck.disponible){
                    Demande demande = demandeService.getById(enginAffecte.getDemande().getNumBCI());
                    Engin engin = enginService.getById(enginAffecte.getEngin().getCodeEngin());
                    if(demande.getQuantiteByFamille(engin.getFamille().getIdFamille())>demande.getNbrAffectByFamille(engin.getFamille().getIdFamille())) {
                        enginAffecte.getEngin().setEtat(EtatEngin.sortie);
                        enginAffecte.setIdDemandeEngin(enginAffecteService.saveEnginDemande(enginAffecte).getIdDemandeEngin());
                        for (Controle controle : enginAffecte.getControleEngin()) {
                            Controle oldControle = controleService.getControlByIdCritereAndIdAffectation(controle.getCritere().getIdCritere(), enginAffecte.getIdDemandeEngin());
                            controle.setId(oldControle == null ? null : oldControle.getId());
                            controleService.save(controle, enginAffecte);
                        }
                        enginService.update(enginAffecte.getEngin());
                        enginAffOld = enginAffecte;
                        enginAffecteeDTOList.add(new EnginAffecteeDTO(enginAffOld));
                    }
                }else throw  new EnginNotDisponibleException("Engin with id ::"+enginAffecte.getEngin().getCodeEngin()+" not disponible");
            }
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
        return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecte) , HttpStatus.OK);
    }

    @PostMapping(value="delete/{numBCI}")
    ResponseEntity<?> deletDemande(@PathVariable("numBCI") Long numBCI) throws AffectDemandDeleteException {
        Demande demande = demandeService.getById(numBCI);
        if(demande.getEnginsAffecteList().isEmpty()){
            demandeService.deletDemande(demande);
            return new ResponseEntity<>("deleted" , HttpStatus.OK);
        }
        throw new AffectDemandDeleteException("Demande Not enable to remove");
    }

    @PostMapping(value="/sortie")
    ResponseEntity<?> sortieEngin(@RequestBody EnginAffecte enginAffecte) throws ResourceNotFoundException, EnginSortieException, ConducteurNotDisponibleException, NullParamException {
        EnginAffecte enginAffecteOld = enginAffecteService.findById(enginAffecte.getIdDemandeEngin()).orElseThrow(
                ()->new ResourceNotFoundException("Affectation not found for this id :: "+enginAffecte.getIdDemandeEngin())
        );
        if(enginAffecteOld.getEtat()==EtatAffectation.enexecution)
            throw new EnginSortieException("Engin alrady exit");
        if(enginAffecte.getConducteur() != null && enginAffecte.getResponsableAffectation()!=null) {
            enginAffecte.getResponsableAffectation().setType(TypeUser.responsable);
            enginAffecte.getConducteur().setEntite(enginAffecteOld.getDemande().getEntite());
            enginAffecte.getConducteur().setType(TypeUser.conducteur);
            Utilisateur conducteur = userService.saveUserIfNotExist(enginAffecte.getConducteur());
            Utilisateur responsable = userService.saveUserIfNotExist(enginAffecte.getResponsableAffectation());
            if(conducteur.isDisponible()){
                enginAffecteOld.setResponsableAffectation(responsable);
                enginAffecteOld.setConducteur(conducteur);
                enginAffecteOld.setEtat(EtatAffectation.enexecution);
                enginAffecteOld.setDateSortie(new Date());
                enginAffecteService.saveEnginDemande(enginAffecteOld);
                enginService.update(enginAffecteOld.getEngin());
                return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecteOld) , HttpStatus.OK);
            }
            throw new ConducteurNotDisponibleException("Condicteur whit matricule :: "+conducteur.getMatricule()+" not disponible");
        }
        throw new NullParamException("Condicteur or Responsable Param is null");
        }
    @PostMapping(value="/entree")
    ResponseEntity<?> entreeEngin(@RequestBody EnginAffecte enginAffecte) throws ResourceNotFoundException {
        EnginAffecte enginAffecteOld = enginAffecteService.findById(enginAffecte.getIdDemandeEngin()).orElseThrow(
                ()->new ResourceNotFoundException("Affectation not found for this id :: "+enginAffecte.getIdDemandeEngin())
        );
        enginAffecteOld.sync(enginAffecte);
        enginAffecteOld.setEtat(EtatAffectation.execute);
        enginAffecteOld.getEngin().setEtat(EtatEngin.disponible);
        enginAffecteOld.setDateEntree(new Date());
        enginAffecteService.saveEnginDemande(enginAffecteOld);
        controleService.saveAll(enginAffecteOld.getControleEngin());
        enginService.update(enginAffecteOld.getEngin());
        return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecteOld) , HttpStatus.OK);
    }


    @PostMapping(value="/submit")
    ResponseEntity<?> submitDemandeSortie(@RequestBody EnginAffecte enginAffecte){
        EnginAffecte enginAffecteOld = enginAffecteService.getById(enginAffecte.getIdDemandeEngin());
        enginAffecteOld.sync(enginAffecte);
        if(enginAffecte.getConducteur() != null && enginAffecte.getResponsableAffectation()!=null){

            enginAffecte.getResponsableAffectation().setType(TypeUser.responsable);
            enginAffecte.getConducteur().setEntite(enginAffecteOld.getDemande().getEntite());
            enginAffecte.getConducteur().setType(TypeUser.conducteur);
            Utilisateur conducteur = userService.saveUserIfNotExist(enginAffecte.getConducteur());
            Utilisateur responsable = userService.saveUserIfNotExist(enginAffecte.getResponsableAffectation());
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


    @RequestMapping(value="/{numBCI}/{idEngin}",method= RequestMethod.GET)
    ResponseEntity<?> ElisteEnginsEntree(@PathVariable(name = "idEngin") String idEngin,@PathVariable(name = "numBCI") Long numBCI) throws ResourceNotFoundException {
        EnginAffecte enginAffecte = enginAffecteService.getByEnginAndDemande(enginService.getById(idEngin),demandeService.getById(numBCI));
        if(enginAffecte == null) throw new ResourceNotFoundException("Affectation not found for this id :: "+enginAffecte.getIdDemandeEngin());
        return new ResponseEntity<>(new DemandeCompletDTO(enginAffecte.getDemande(), Arrays.asList(new EnginDTO(enginAffecte.getEngin(),enginAffecte))) , HttpStatus.OK);
    }


    /*

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
     */

}
