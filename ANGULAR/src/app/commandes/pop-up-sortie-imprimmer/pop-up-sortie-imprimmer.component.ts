import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommandesService } from '../commandes.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { startWith,map } from 'rxjs/operators';


export class Users {
  matricule: string;
  nom: string;
  prenom: string;
}

export class responsableAffectation {
  matricule: string;
  nom: string;
  prenom: string;

  constructor(matricule,nom,prenom){
    this.matricule = matricule
    this.nom = nom
    this.prenom = prenom

  }
}

export class conducteur {
  matricule: string;
  nom: string;
  prenom: string;
  constructor(matricule,nom,prenom){
    this.matricule = matricule
    this.nom = nom
    this.prenom = prenom

  }
}
export class sortie {
  idDemandeEngin: string;
  responsableAffectation: responsableAffectation;
  conducteur: conducteur;
}


@Component({
  selector: 'app-pop-up-sortie-imprimmer',
  templateUrl: './pop-up-sortie-imprimmer.component.html',
  styleUrls: ['./pop-up-sortie-imprimmer.component.scss']
})
export class PopUpSortieImprimmerComponent  {
  ConducteurMatricule = new FormControl();
  ResponsableMatricule = new FormControl();
  idDemandeEngin = null;
  filteredUsers: Observable<Users[]>;
  filteredResponsable: Observable<Users[]>;
  sortie_ = new sortie()

  Users:Array<Users> = [  ];
  Responsables:Array<Users> = [  ];
  conducteurMatricule= null
  responsableMatricule= null
  disableConducteur = false
  disableResponsable = false


  conducteur = new FormGroup({
      ConducteurMatricule: new FormControl(),
      ConducteurNom: new FormControl(),
      ConducteurPrenom: new FormControl(),
    }
  )
  responsable = new FormGroup({
      ResponsableMatricule: new FormControl(),
      ResponsableNom: new FormControl(),
      ResponsablePrenom: new FormControl(),
    }
  )




  constructor(public commandesService :CommandesService,public dialogRef: MatDialogRef<PopUpSortieImprimmerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,) {
     this.idDemandeEngin = data.engin.value.idDemandeEngin

    this.commandesService.users().subscribe((data:any)=>{
      console.log(data._embedded.users)
      this.Users = data._embedded.users
      this.Responsables = data._embedded.users

      this.filteredResponsable = this.ResponsableMatricule.valueChanges.pipe(
        startWith(''),
        map(Responsables => (Responsables ? this._filterResponsables(Responsables) : this.Responsables.slice())),
      );

      this.filteredUsers = this.ConducteurMatricule.valueChanges.pipe(
        startWith(''),
        map(User => (User ? this._filterUsers(User) : this.Users.slice())),
      );

    })








  }




  private _filterUsers(value: string): Users[] {





   let cnd = this.Users.filter(user => user.matricule?.toLowerCase()?.includes(this.ConducteurMatricule?.value?.toString()?.toLowerCase()))
    this.conducteurMatricule =  ((cnd.length == 0) || (this.ConducteurMatricule?.value?.toString()?.toLowerCase() == '' )) ? null : cnd;
    let findConducteur = this.Users.filter(Conducteur => Conducteur?.matricule?.toLowerCase() == this.ConducteurMatricule?.value?.toString()?.toLowerCase())[0]
   console.log(findConducteur)
    if((findConducteur == undefined) || (findConducteur == null)){
      this.disableConducteur=false;
      this.conducteur.get("ConducteurNom").setValue('')
      this.conducteur.get("ConducteurPrenom").setValue('')

    }
    else{
      this.conducteur.get("ConducteurNom").setValue(findConducteur.nom)
      this.conducteur.get("ConducteurPrenom").setValue(findConducteur.prenom)
      this.disableConducteur=true
    }



    const filterValue = value.toLowerCase();
    return this.Users.filter(User => User.matricule?.toLowerCase().includes(filterValue));
  }
  private _filterResponsables(value: string): Users[] {



    let cnd = this.Responsables.filter(Responsable => Responsable?.matricule?.toLowerCase()?.includes(this.ResponsableMatricule?.value?.toString()?.toLowerCase()))
    this.responsableMatricule =  ((cnd.length == 0) || (this.ResponsableMatricule?.value?.toString()?.toLowerCase() == '' )) ? null : cnd;
    let findResponsable = this.Responsables.filter(Responsable => Responsable?.matricule?.toLowerCase() == this.ResponsableMatricule?.value?.toString()?.toLowerCase())[0]
    if((findResponsable == undefined) || (findResponsable == null)){
      this.disableResponsable=false;
      this.responsable.get("ResponsableNom").setValue('')
      this.responsable.get("ResponsablePrenom").setValue('')

    }
    else{
      this.responsable.get("ResponsableNom").setValue(findResponsable.nom)
      this.responsable.get("ResponsablePrenom").setValue(findResponsable.prenom)
      this.disableResponsable=true
    }




    const filterValue = value.toLowerCase();
    return this.Responsables.filter(Responsable => Responsable.matricule?.toLowerCase()?.includes(filterValue));
  }
  checkifExiste(user){
    if(user == "conducteur" ){

    }
    else if(user == "responsable" ){

      }
  }
  error_message = ""
  sortie(){


    console.log(this.conducteur)
    this.sortie_.conducteur = new conducteur(this.ConducteurMatricule?.value,this.conducteur.get("ConducteurNom")?.value,this.conducteur.get("ConducteurPrenom")?.value)
    this.sortie_.responsableAffectation =  new conducteur(this.ResponsableMatricule?.value,this.responsable.get("ResponsableNom")?.value,this.responsable.get("ResponsablePrenom")?.value)
    this.sortie_.idDemandeEngin =  this.idDemandeEngin


    if(
      this.ConducteurMatricule?.value == undefined || this.ConducteurMatricule?.value == null || this.ConducteurMatricule?.value == ""||
      this.ResponsableMatricule?.value == undefined || this.ResponsableMatricule?.value == null || this.ResponsableMatricule?.value == ""
      ){

        this.error_message = "Veuillez remplir tous les champs"
    }
    else{
      this.commandesService.sortie(this.sortie_).subscribe((data:any)=>{
        this.dialogRef.close(this.sortie_);


       })
    }


  }
}
