import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ParametresService {

  constructor(private http: HttpClient) { }

  //webServices Engin
   listeEngins(){
    return this.http.get(environment.url_prod+'/engins/');
    }

    deleteEngin(id){
      return this.http.delete(environment.url_prod+`/engins/${id}`);
    }
    PutEngin(listeEngins)
    {
     return this.http.put(environment.url_prod+`/engins/${listeEngins.codeEngin}`,listeEngins);
    }
    addEngins(listEngins){

      return this.http.post(environment.url_prod+'/engin/addEngins',listEngins );
    }
   //webServices Critere
   deleteCritere(id){
    return this.http.delete(environment.url_prod+`/criteres/${id}`);
  }

  PutCritere(listcritere)
  {
   return this.http.put(environment.url_prod+`/criteres/${listcritere.idCritere}`,listcritere);
  }
  listeCritere(){

    return this.http.get(environment.url_prod+'/criteres');
  }
  addcritere(NomCritere){
    return this.http.post(environment.url_prod+'/criteres',NomCritere );
  }
  /////// WebServices Familles
  deleteFamil(id){
    return this.http.delete(environment.url_prod+`/familles/${id}`);
  }

  PutFamil(listeFamil)
  {
   return this.http.put(environment.url_prod+`/familles/${listeFamil.idFamille}`,listeFamil);
  }
  listeFamil(){

    return this.http.get(environment.url_prod+'/familles/' );
  }

  addFamil(listeFamil){
    return this.http.post(environment.url_prod+'/familles/',listeFamil );
  }




  //web services Postes
  listePosts(){

    return this.http.get(environment.url_prod+'/posts/get' );
  }

  addPost(Listeposts){
    return this.http.post(environment.url_prod+'/posts/add',Listeposts);

   }

   deletePost(id){
    return this.http.delete(environment.url_prod+`/post/${id}`);
  }

  PutPost(Listeposts){
    return this.http.put(environment.url_prod+`/posts/`,Listeposts);

  }
   //Xebservices Entites

  addEntite(listeEntites){
   return this.http.post(environment.url_prod+'/entites/',listeEntites );
  }
  deleteEntite(id){
    return this.http.delete(environment.url_prod+`/entites/${id}`);
  }


  listeEntite(){
    return this.http.get(environment.url_prod+'/entites/' );
  }


  PutEntite(listeEntites){
    return this.http.put(environment.url_prod+`/entites/${listeEntites.id}`,listeEntites);
 }

   //web services Utilisateur
   addUser(listeUser){
    return this.http.post(environment.url_prod+'/users/',listeUser );
   }
   listeUser(){
   return this.http.get(environment.url_prod+'/user' );
  }

  deleteUser(id){
    return this.http.delete(environment.url_prod+`/users/${id}`);
  }
  PutUser(listeUser){
    return this.http.post(environment.url_prod+'/user/',listeUser);

  }
}
