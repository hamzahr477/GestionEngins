import { CommandesService } from './../commandes.service';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-engin',
  templateUrl: './engin.component.html',
  styleUrls: ['./engin.component.scss']
})
export class EnginComponent implements OnInit {
  itemsCriteres: FormArray;
  sortie : null;
  entree : string;


  FGEnginCritere =new FormGroup({

    codeEngin:new FormControl(),
    typeEngin: new FormControl(),
    capacite: new FormControl(),
    compteur: new FormControl(),
    dateDernierAffectation: new FormControl(),
    famille: new FormControl(),
    critere: new FormArray([])
  });
  FGConducteur =new FormGroup({

    matricule:new FormControl(),
    nom: new FormControl(),
    prenom: new FormControl(),

  });
  FGResponsableAffectation =new FormGroup({

    matricule:new FormControl(),
    nom: new FormControl(),
    prenom: new FormControl(),

  });


  constructor(public commandesService :CommandesService,private fb: FormBuilder) {
    this.commandesService.engin('N102547').subscribe((data:any)=>{
      // this.FGConducteur?.setValue(data?.conducteur)
      // this.FGResponsableAffectation?.setValue(data?.responsableAffectation)




      this.itemsCriteres = this.FGEnginCritere.get('critere') as FormArray;
      this.FGEnginCritere.get('codeEngin').setValue(data.codeEngin)
      this.FGEnginCritere.get('typeEngin').setValue(data.typeEngin)
      this.FGEnginCritere.get('capacite').setValue(data.capacite)
      this.FGEnginCritere.get('compteur').setValue(data.compteur)

      this.entree =  data["critere"][0].observationEntree
      this.sortie =  data["critere"][0].observationSortie
      data["critere"].forEach( critere => {
        critere.etatEntree ==  'e' ? this.entree = "e": null

        this.itemsCriteres.push(this.fb.group({
          critere: critere.critere,
          etatEntree: critere.etatEntree,
          etatSortie: critere.etatSortie,
          idCritere: critere.idCritere,
          observationEntree: critere.observationEntree,
          observationSortie: critere.observationSortie,
        }));


      });




    })
  }
  ngOnInit(): void {
  }

  getCriteres() {
    return this.FGEnginCritere.controls.critere["controls"];
  }

}
