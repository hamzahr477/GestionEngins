import { post } from './nouvelle-commande/nouvelle-commande.component';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommandesService {



  constructor(private http: HttpClient) { }

  listeDemandes(){
    return this.http.get(environment.url_prod+'/demandes/all' )
  }
  listeEnginsSortie(){
    return this.http.get(environment.url_prod+'/engin/listeEnginsSortie' )
  }
  listeEnginsEntree(){
    return this.http.get(environment.url_prod+'/engin/listeEnginsEntree' )
  }
  listeEngins(){
    return this.http.get(environment.url_prod+'/engin/listeEngins' )
  }
  listeEnginsDisponible(famille){
    return this.http.get(environment.url_prod+'/engin/listeEnginsDisponible/'+famille )
  }
  supenginaffect(enginAffecter){
    return this.http.post(environment.url_prod+'/demandes/supenginaffect/',enginAffecter )
  }

  listeCriteres(){
    return this.http.get(environment.url_prod+'/criteres' )
  }

  demande(numBCI){
    return this.http.get(environment.url_prod+'/demandes/'+numBCI )
  }
  getDemandeByEngin(engin){
    return this.http.get(environment.url_prod+'/demandes/engin/'+engin )
  }
  engin(codeEngin){
    return this.http.get(environment.url_prod+'/engin/'+codeEngin )
  }
  users(){
    return this.http.get(environment.url_prod+'/users/' )
  }
  sortie(sortie){
    return this.http.post(environment.url_prod+'/demandes/submit',sortie )
  }
  addPagne(pannes){
    return this.http.post(environment.url_prod+'/pagnes/addPagne',pannes )
  }

  familles(){
    return this.http.get(environment.url_prod+'/familles' )
  }

  ajouterDemande(demande){
    return this.http.post(environment.url_prod+'/demandes/add',demande )
  }
  ajouterAffecattion(engins){
    return this.http.post(environment.url_prod+'/demandes/affecter',engins )
  }
  ajouterControler(engins){
    return this.http.post(environment.url_prod+'/demandes/controler',engins )
  }
  reserver(listEnginReserver){
    return this.http.post(environment.url_prod+'/demandes/reserver',listEnginReserver )
  }

  listePosts(){

    return this.http.get(environment.url_prod+'/posts/get' );
  }
  listeEntite(){
    return this.http.get(environment.url_prod+'/entites/' );
  }
}
