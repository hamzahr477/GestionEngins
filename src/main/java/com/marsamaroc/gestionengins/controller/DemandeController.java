package com.marsamaroc.gestionengins.controller;

import com.marsamaroc.gestionengins.dto.*;
import com.marsamaroc.gestionengins.entity.*;
import com.marsamaroc.gestionengins.enums.DisponibiliteEnginParck;
import com.marsamaroc.gestionengins.enums.EtatAffectation;
import com.marsamaroc.gestionengins.enums.EtatEngin;
import com.marsamaroc.gestionengins.enums.TypeUser;
import com.marsamaroc.gestionengins.exception.*;
import com.marsamaroc.gestionengins.mapper.EntityToDTO;
import com.marsamaroc.gestionengins.repository.UserRepository;
import com.marsamaroc.gestionengins.response.APIResponseDemandeState;
import com.marsamaroc.gestionengins.service.*;
import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/demandes")
public class DemandeController {

    @Autowired
    DetailsDemandeService detailsDemandeService;
    @Autowired
    ShiftService shiftService;
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
    ResponseEntity<?> getAllDemande() throws ResourceNotFoundException {
        List<Demande> demandeList= demandeService.findAllDemande();
        if(demandeList.isEmpty())
            throw new ResourceNotFoundException("Demandes Not Found");
        List<APIResponseDemandeState<DemandeDTO>> demandeDTOList = new ArrayList<>();
        for(Demande demande : demandeList){
                demandeDTOList.add(new APIResponseDemandeState<>(new DemandeDTO(demande),demande.isValableToTrait(Shift.nextShift(shiftService.findAll()).getHeureFin())));
        }
        Collections.sort(demandeDTOList, Comparator.comparing(APIResponseDemandeState::getValable));
        return new ResponseEntity<>(demandeDTOList,HttpStatus.OK);
    }


    @RequestMapping(value="/enregistree",method= RequestMethod.GET)
    ResponseEntity<?> getDemandeEnregistree(@RequestParam(required = false,name="offset",defaultValue = "0") int offset,
                                            @RequestParam(required = false,name="pageSize", defaultValue = "20") int pageSize) throws ResourceNotFoundException {
        Page<Demande> demandePage= demandeService.getAvailbleDemandeEnregistrer(Shift.nextShift(shiftService.findAll()),offset,pageSize);
        EntityToDTO<Demande, DemandeDTO> entityToDTO = new EntityToDTO<>(Demande.class,DemandeDTO.class);
        return new ResponseEntity<>(entityToDTO.mappingToDTO(demandePage) , HttpStatus.OK);
    }
    @RequestMapping(value="/verifiee",method= RequestMethod.GET)
    ResponseEntity<?> getDemandeVerifiee(@RequestParam(required = false,name="offset",defaultValue = "0") int offset,
                                         @RequestParam(required = false,name="pageSize", defaultValue = "20") int pageSize) throws ResourceNotFoundException {
        Page<Demande> demandePage= demandeService.getAvailbleDemandeVerifier(Shift.nextShift(shiftService.findAll()),offset,pageSize);
        EntityToDTO<Demande, DemandeDTO> entityToDTO = new EntityToDTO<>(Demande.class,DemandeDTO.class);
        return new ResponseEntity<>(entityToDTO.mappingToDTO(demandePage) , HttpStatus.OK);
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
    ResponseEntity<?> reserveEnins(@RequestBody List<EnginAffecte> enginAffecteList) throws ResourceNotFoundException, EnginNotDisponibleException, DemandeClotureException {
        List<EnginAffecteeDTO>  enginAffecteeDTOList = new ArrayList<>();
        Demande demande_ =enginAffecteList.isEmpty() ?null:  demandeService.getById(enginAffecteList.get(0).getDemande().getNumBCI());
        if(demande_!=null) {
            System.out.println("Date  : :" +LocalDateTime.of(demande_.getDateSortie().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), demande_.getShift().getHeureFin())+" : "+LocalDateTime.now(Clock.systemUTC()));
            if (LocalDateTime.of(demande_.getDateSortie().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), demande_.getShift().getHeureFin()).compareTo(LocalDateTime.now(Clock.systemUTC())) <     1)
                throw new DemandeClotureException("Date demande has been passed for numBCI :: " + demande_.getNumBCI());
        } for ( EnginAffecte enginAffecte :  enginAffecteList){
            Engin engin_ = enginService.findById(enginAffecte.getEngin().getCodeEngin()).orElseThrow(
                    ()->new ResourceNotFoundException("Engin not found for this id :: "+enginAffecte.getEngin().getCodeEngin())
            );
            EnginAffecte enginAffOld = enginAffecteService.getByEnginAndDemande(enginAffecte.getEngin(),enginAffecte.getDemande());
            if(enginAffOld == null ){
                if(engin_.getEtat() == EtatEngin.parcking && engin_.getDisponibiliteParck() == DisponibiliteEnginParck.disponible){
                    Demande demande = demandeService.getById(enginAffecte.getDemande().getNumBCI());
                    Engin engin = enginService.getById(enginAffecte.getEngin().getCodeEngin());
                    if(demande.getQuantiteByFamille(engin.getFamille().getIdFamille())>demande.getNbrAffectByFamille(engin.getFamille().getIdFamille())) {
                        enginAffecte.setIdDemandeEngin(enginAffecteService.saveEnginDemande(enginAffecte).getIdDemandeEngin());
                        for (Controle controle : enginAffecte.getControleEngin()) {
                            Controle oldControle = controleService.getControlByIdCritereAndIdAffectation(controle.getCritere().getIdCritere(), enginAffecte.getIdDemandeEngin());
                            controle.setId(oldControle == null ? null : oldControle.getId());
                            controleService.save(controle, enginAffecte);
                        }
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
            enginAffecte.getEngin().setEtat(EtatEngin.parcking);
            enginService.update(enginAffecte.getEngin());
        }
        return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecte) , HttpStatus.OK);
    }

    @PostMapping(value="delete/{numBCI}")
    ResponseEntity<?> deletDemande(@PathVariable("numBCI") Long numBCI) throws AffectDemandDeleteException, ResourceNotFoundException {
        Demande demande = demandeService.getById(numBCI);
        if(demande.getEnginsAffecteList().isEmpty()){
            demandeService.deletDemande(demande);
            return new ResponseEntity<>("deleted" , HttpStatus.OK);
        }
        throw new AffectDemandDeleteException("Demande Not enable to remove");
    }

    @PostMapping(value="/sortie")
    ResponseEntity<?> sortieEngin(@RequestBody EnginAffecteeSEDTO enginAffecteeSEDTO) throws ResourceNotFoundException, EnginSortieException, ConducteurNotDisponibleException, NullParamException, DemandeClotureException {

        EnginAffecte enginAffecteOld = enginAffecteService.findById(enginAffecteeSEDTO.getIdDemandeEngin()).orElseThrow(
                ()->new ResourceNotFoundException("Affectation not found for this id :: "+enginAffecteeSEDTO.getIdDemandeEngin())
        );
        if(enginAffecteOld.getEtat()==EtatAffectation.enexecution)
            throw new EnginSortieException("Engin alrady exit");
        if (LocalDateTime.of(enginAffecteOld.getDemande().getDateSortie().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), enginAffecteOld.getDemande().getShift().getHeureFin()).compareTo(LocalDateTime.now(Clock.systemUTC())) <     1)
            throw new DemandeClotureException("Date demande has been passed for numBCI :: " + enginAffecteOld.getDemande().getNumBCI());
        EnginAffecte enginAffecte = new EnginAffecte();
        enginAffecte.setConducteur_sortie(enginAffecteeSEDTO.getConducteur());
        enginAffecte.setResponsableAffectation_sortie(enginAffecteeSEDTO.getResponsableAffectation());
        enginAffecte.setControleEngin(enginAffecteeSEDTO.getControleEngin());
        if(enginAffecte.getConducteur_sortie() != null && enginAffecte.getResponsableAffectation_sortie()!=null) {
            enginAffecte.getResponsableAffectation_sortie().setType(TypeUser.responsable);
            enginAffecte.getConducteur_sortie().setEntite(enginAffecteOld.getDemande().getEntite());
            enginAffecte.getConducteur_sortie().setType(TypeUser.conducteur);
            Utilisateur conducteur = userService.saveUserIfNotExist(enginAffecte.getConducteur_sortie());
            Utilisateur responsable = userService.saveUserIfNotExist(enginAffecte.getResponsableAffectation_sortie());
            enginAffecteOld.setCompteur_sortie(enginAffecteeSEDTO.getCompteur());
            enginAffecteOld.setResponsableAffectation_sortie(responsable);
            enginAffecteOld.setConducteur_sortie(conducteur);
            enginAffecteOld.setShift_sortie(Shift.currrentShift(shiftService.findAll()));
            enginAffecteOld.setEtat(EtatAffectation.enexecution);
            enginAffecteOld.setDateSortie(new Date());
            enginAffecteOld.setObservation_sortie(enginAffecteeSEDTO.getObservation());
            enginAffecteService.saveEnginDemande(enginAffecteOld);
            enginAffecteOld.getEngin().setCompteur(enginAffecteeSEDTO.getCompteur());
            enginAffecteOld.getEngin().setEtat(EtatEngin.sortie);
            enginService.update(enginAffecteOld.getEngin());
            return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecteOld) , HttpStatus.OK);
           }
        throw new NullParamException("Condicteur or Responsable Param is null");
        }
    @Autowired
    PanneService panneService;

    @PostMapping(value="/entree")
    ResponseEntity<?> entreeEngin(@RequestBody EnginAffecteeSEDTO enginAffecteeSEDTO) throws ResourceNotFoundException, EnginSortieException, NullParamException {
        EnginAffecte enginAffecteOld = enginAffecteService.findById(enginAffecteeSEDTO.getIdDemandeEngin()).orElseThrow(
                ()->new ResourceNotFoundException("Affectation not found for this id :: "+enginAffecteeSEDTO.getIdDemandeEngin())
        );
        if(enginAffecteOld.getEtat()==EtatAffectation.execute)
            throw new EnginSortieException("Engin alrady enter");
        if(enginAffecteeSEDTO.getConducteur() != null && enginAffecteeSEDTO.getResponsableAffectation()!=null){
            enginAffecteeSEDTO.getResponsableAffectation().setType(TypeUser.responsable);
            enginAffecteeSEDTO.getConducteur().setEntite(enginAffecteOld.getDemande().getEntite());
            enginAffecteeSEDTO.getConducteur().setType(TypeUser.conducteur);
            Utilisateur conducteur = userService.saveUserIfNotExist(enginAffecteeSEDTO.getConducteur());
            Utilisateur responsable = userService.saveUserIfNotExist(enginAffecteeSEDTO.getResponsableAffectation());
            enginAffecteOld.setResponsableAffectation_entree(responsable);
            enginAffecteOld.setConducteur_entree(conducteur);
            enginAffecteOld.setControleEngin(enginAffecteeSEDTO.getControleEngin());
            enginAffecteOld.setCompteur_entree(enginAffecteeSEDTO.getCompteur());
            List<DetailsPanne> detailsPanneList = new ArrayList<>();
            for(Controle controle : enginAffecteOld.getControleEngin()){
                controle.setId(controleService.getControlByIdCritereAndIdAffectation(controle.getCritere().getIdCritere(),enginAffecteOld.getIdDemandeEngin()).getId());
                controleService.save(controle,enginAffecteOld);
                if(controle.getEtatEntree()=='N'){
                    DetailsPanne detailsPanne = new DetailsPanne();
                    detailsPanne.setCritere(controle.getCritere());
                    detailsPanne.setObservation("Entre de l'engin aprés l'affectation de la demande : "+enginAffecteOld.getDemande().getNumBCI());
                    detailsPanneList.add(new DetailsPanne());
                }
            }
            if(!detailsPanneList.isEmpty()){
                Panne panne = new Panne();
                panne.setDetailsPanneList(detailsPanneList);
                panne.setEngin(enginAffecteOld.getEngin());
                panne.setCurrentDemande(enginAffecteOld.getDemande());
                panne.setDernierAffectation(enginAffecteOld.getEngin().getDerniereAffectation());
                panneService.saveOrUpdatePagne(panne);
            }
            enginAffecteOld.getEngin().setEtat(EtatEngin.parcking);
            enginAffecteOld.getEngin().setCompteur(enginAffecteeSEDTO.getCompteur());
            enginAffecteOld.setEtat(EtatAffectation.execute);
            enginAffecteOld.setShift_entree(Shift.currrentShift(shiftService.findAll()));
            enginAffecteOld.setDateEntree(new Date());
            enginAffecteOld.setObservation_entree(enginAffecteeSEDTO.getObservation());
            enginAffecteService.saveEnginDemande(enginAffecteOld);
            enginService.update(enginAffecteOld.getEngin());
            return new ResponseEntity<>(new EnginAffecteeDTO(enginAffecteOld) , HttpStatus.OK);
        }
        throw new NullParamException("Condicteur or Responsable Param is null");
    }


    /*
    *
    * @PostMapping(value="/submit")
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
            enginAffecteOld.getEngin().setEtat(EtatEngin.parcking);
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

    * */


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
