import { style } from '@angular/animations';
import { DatePipe } from '@angular/common';
import { CommandesService } from './../commandes.service';
import { PopUpListEnginComponent } from './../pop-up-list-engin/pop-up-list-engin.component';
import { NgbDateAdapter, NgbDateParserFormatter, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, ViewChild, ElementRef, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FormArray, FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { PopUpSortieImprimmerComponent } from '../pop-up-sortie-imprimmer/pop-up-sortie-imprimmer.component';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { PopUpReportPanneComponent } from '../pop-up-report-panne/pop-up-report-panne.component';
import jsPDF from 'jspdf';
import htmlToPdfmake from 'html-to-pdfmake';
import html2canvas from 'html2canvas';

import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { PopUpConfirmComponent } from '../pop-up-confirm/pop-up-confirm.component';
import {MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS} from '@angular/material-moment-adapter';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import * as moment from 'moment';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Injectable()
export class CustomAdapter extends NgbDateAdapter<string> {

  readonly DELIMITER = '-';

  fromModel(value: string | null): NgbDateStruct | null {
    if (value) {
      const date = value.split(this.DELIMITER);
      return {
        day : parseInt(date[0], 10),
        month : parseInt(date[1], 10),
        year : parseInt(date[2], 10)
      };
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): string | null {
    //return date ? date.day + this.DELIMITER + `${date.month}`.padStart(2, "0")   + this.DELIMITER + date.year : null;
    return date ? date.day + this.DELIMITER + `${date.month}`.padStart(2, "0")   + this.DELIMITER + date.year : null;
  }
}

/**
 * This Service handles how the date is rendered and parsed from keyboard i.e. in the bound input field.
 */
@Injectable()
export class CustomDateParserFormatter extends NgbDateParserFormatter {

  readonly DELIMITER = '/';

  parse(value: string): NgbDateStruct | null {
    if (value) {
      const date = value.split(this.DELIMITER);
      return {
        day : parseInt(date[0], 10),
        month : parseInt(date[1], 10),
        year : parseInt(date[2], 10)
      };
    }
    return null;
  }

  format(date: NgbDateStruct | null): string {
    return date ? date.day + this.DELIMITER + date.month + this.DELIMITER + date.year : '';
  }
}

export class post{
  codePost:Number
  constructor(codePoste:number){
    this.codePost = codePoste
  }

}
export class famille{
  idFamille:number

}
export class detailsDemandeList{
  famille:famille
  quantite:number
  remarque:string
}

export class demande{
  numBCI:number
  dateDemande:String
  dateSortie:String
  engins:String
  shift:number
  entite:String
  post:post
  detailsDemandeList:Array<detailsDemandeList>

  constructor(numBCI) {
    this.numBCI = numBCI
  }
}
export class affectationEngin{
  demande:demande
  engin :engin
}
export class engin{
  codeEngin :string
  constructor(codeEngin){
    this.codeEngin= codeEngin
  }
}
export class listFamille{
  idFamille :string
  nomFamille:string
}
export class critere{
  idCritere :string
  nomCritere :string
  etatSortie :string
  etatEntree :string
  obsSortie :string
  osbEntree :string


  constructor(idCritere){
    this.idCritere= idCritere
  }

}
export class EnginAffecte{
  idDemandeEngin :string
  controleEngin :Array<controleEngin>

}
export class controleEngin{
  critere :critere
  etatSortie :string
  etatEntree :string
  obsSortie :string
  osbEntree :string



}


export class EnginReserves{
  demande:demande
  engin:engin
  controleEngin:Array<controleEngin>

}


export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};


@Component({
  selector: 'app-nouvelle-commande',
  templateUrl: './nouvelle-commande.component.html',
  styleUrls: ['./nouvelle-commande.component.scss'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],

    },
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter},

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],
})
export class NouvelleCommandeComponent implements OnInit {


  model: NgbDateStruct;

  DateSortie = "aa"
  DetailCMDisCollapsed = false
  EnginDemmandeisCollapsed = false
  numBCI = null
  id_famille=""
  nomFamille=""
  list_famille: Array<listFamille>
  list_criteres:Array<critere> = null


  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';

  getFamilleById(id_famille ){
    if(id_famille == null){
      return
    }
    console.log(id_famille )
    this.list_famille.forEach( famille => {
      console.log(famille)
      if(famille.idFamille.toString() == id_famille.toString()){
        console.log(famille.nomFamille)

        this.nomFamille =famille.nomFamille
        return
      }

    })
  }

  DELIMITER = '-'
  convertDateToDB(date){

    var momentVariable = moment(date, 'DD/MM/YYYY');
    var stringvalue = momentVariable.format('YYYY-MM-DD');


    return stringvalue + "T02:46:11.932+00:00"


      //return date ? date.day + this.DELIMITER + `${date.month}`.padStart(2, "0")   + this.DELIMITER + date.year : null;
      // return date ? date.day + this.DELIMITER + `${date.month}`.padStart(2, "0")   + this.DELIMITER + date.year : null;

  }

  //   ConverttoDate(date: NgbDate): string // from internal model -> your mode
  // {
  //   return date ? date.year + "" + ('0' + date.month).slice(-2)
  //     + "" + ('0' + date.day).slice(-2) : null
  // }

  posts_value= []
  FGCommande:FormGroup
  FGEnginDemande:FormGroup
  FGEngins:FormGroup
  FGEnginCritere:FormGroup
  sortie = null
  entree = null


  formFamille = new FormGroup({


      idFamille : new FormControl(),
      nomFamille:  new FormControl(),
      icon:  new FormControl(),
      quantite: new FormControl("1"),
      remarque: new FormControl(),

  })

  itemsFamilles: FormArray;
  itemsEngins: FormArray = null;



  animal: string;
  name: string;


  createFGEnginCritere() {
    this.FGEnginCritere = this.fb.group({
      engins: this.fb.array([])
    })
  }

  createEnginsArray(engin) {
    console.log(engin)
    return this.fb.group({


      idDemandeEngin: engin.idDemandeEngin,
      codeEngin: engin.codeEngin,
      typeEngin: engin.typeEngin,
      capacite: engin.capacite,
      compteur: engin.compteur,
      dateDernierAffectation: engin.dateDernierAffectation,
      famille: engin.famille,
      critere: new FormArray([]),
      entree: engin.entree,
      sortie: engin.sortie,
      conducteur: engin.conducteur,
      responsable: engin.responsable,
    });
  }
  createCriteresArray(critere) {
    return this.fb.group({
      nomCritere: critere.nomCritere,
      etatEntree: critere.etatEntree,
      etatSortie: critere.etatSortie,
      idCritere: critere.idCritere,
      observationEntree: critere.observationEntree,
      observationSortie: critere.observationSortie,
    })
  }

  getEngins(form) {
    return form.controls.engins.controls;
  }

  getCriteres(form) {
    return form.controls.critere.controls;
  }

  addEngin(engin) {
    console.log(engin)
    const control = <FormArray>this.FGEnginCritere.get('engins');
    control.push(this.createEnginsArray(engin));
  }
  addCriteres(critere,codeEngin) {
    <FormArray>this.FGEnginCritere.get('engins')["controls"].forEach(element => {
      console.log(element.get('codeEngin').value)

       if(element.get('codeEngin').value == codeEngin) {
          const control = element.get('critere');
          control?.push(this.createCriteresArray(critere));
          console.log(control);
       }

    });



  }

  entreeEngin(engin){
    console.log(engin)
    let enginAffecte = new EnginAffecte();
    enginAffecte.idDemandeEngin = engin.idDemandeEngin.value
    enginAffecte.controleEngin = engin.critere.value


    console.log(enginAffecte)


    this.commandesService.sortie(enginAffecte).subscribe((data:any)=>{
      const control = <FormArray>this.FGEnginCritere.get('engins');
      control.at(control.value.findIndex(engin_ => engin_.codeEngin === engin.codeEngin.value) ).get("entree").patchValue("o")

      console.log(control.at(control.value.findIndex(engin_ => engin_.codeEngin === engin.codeEngin.value) ))

      this.openSnackBar("Retour engin "+engin.codeEngin.value+" avec success")


     })


  }



  title = 'htmltopdf';

  @ViewChild('pdfTable') pdfTable: ElementRef;

  public downloadAsPDF() {
    const doc = new jsPDF();

    const pdfTable = this.pdfTable.nativeElement;

    var html = htmlToPdfmake(pdfTable.innerHTML);

    const documentDefinition = { content: html };
    pdfMake.createPdf(documentDefinition).open();

  }

show = false;
size = "16"

  public exportHtmlToPDF(code_Engin){



    let headerHeight = 0
    let height_info_fiche = 0
    let height_data = 0
    let header = document.getElementById('header_'+code_Engin);
    let data = document.getElementById('data_'+code_Engin);
    let info_fiche = document.getElementById('info_'+code_Engin);
    let signature = document.getElementById('signature_'+code_Engin);
    let doc = new jsPDF('p', 'mm', 'a4');

    header.style.fontSize = '30px'


      html2canvas(header).then(canvas => {

        let headerWidth = 208;
        headerHeight = canvas.height * headerWidth / canvas.width;

        const contentDataURL = canvas.toDataURL('image/png')
        doc.addImage(contentDataURL, 'PNG', 0, 10, headerWidth, headerHeight)

              html2canvas(info_fiche).then(canvas => {

                let docWidth = 208;
                let docHeight = canvas.height * docWidth / canvas.width;
                height_info_fiche = docHeight

                const contentDataURL = canvas.toDataURL('image/png')

                doc.addImage(contentDataURL, 'PNG', 0, headerHeight +20, docWidth, docHeight)

                    html2canvas(data).then(canvas_info_fiche => {

                      let docWidth = 208;
                      let canvas_info_fiche_Height = canvas_info_fiche.height * docWidth / canvas_info_fiche.width;
                      height_data = canvas_info_fiche_Height

                      const contentDataURL_info_fiche = canvas_info_fiche.toDataURL('image/png')

                      doc.addImage(contentDataURL_info_fiche, 'PNG', 0, height_info_fiche + headerHeight + 30, docWidth, canvas_info_fiche_Height)



                            html2canvas(signature).then(canvas_signature => {

                              let docWidth = 208;
                              let canvas_signature_Height = canvas_signature.height * docWidth / canvas_signature.width;

                              const contentDataURL_signature = canvas_signature.toDataURL('image/png')

                              doc.addImage(contentDataURL_signature, 'PNG', 0, height_data +height_info_fiche + headerHeight + 60, docWidth, canvas_signature_Height)
                              doc.save('exportedPdf.pdf');

                            })
                    })

            });

      });









  }

  openSnackBar(message) {
    this._snackBar.open(message, 'Fermer', {
      duration: 3 * 1000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      panelClass:"custom_sneak_bar"

    });
  }


  openDialogSortieImprimmer(engin,i): void {


    const dialogRef = this.dialog.open(PopUpSortieImprimmerComponent, {
      width: '1000px',
      data: {engin: engin},
    });

    dialogRef.afterClosed().subscribe(engin => {

      let FGEnginCritere = this.FGEnginCritere.get('engins') as FormArray;


      console.log(engin)
      FGEnginCritere.at(i).get("conducteur").patchValue(engin.conducteur);
      FGEnginCritere.at(i).get("responsable").patchValue(engin.responsableAffectation);
      console.log(FGEnginCritere)

      this.openSnackBar("Sortie d'engin "+engin.codeEngin.value+" avec success")


    })
  }


  openDialogEngins(item,etat,index,idFamille): void {

    let enginSelecter = []
    const control = <FormArray>this.FGEnginCritere.get('engins');
    control.value.forEach(element => {
      console.log(element.codeEngin)
      enginSelecter.push(element.codeEngin)

    });

    const dialogRef = this.dialog.open(PopUpListEnginComponent, {
      width: '1000px',
      data: {famille: this.itemsFamilles,engin: this.itemsFamilles,nb:1,etat:etat,idFamille:idFamille,enginSelecter:enginSelecter},
    });

    dialogRef.afterClosed().subscribe(engin => {



       if(etat == "change"){
        let FGEngins = this.FGEngins.get('items') as FormArray;


        console.log( engin)
        FGEngins.at(index).get("idDemandeEngin").patchValue(null);
        FGEngins.at(index).get("idEnginAffect").patchValue(null);
        FGEngins.at(index).get("codeEngin").patchValue(engin[0].codeEngin);
        FGEngins.at(index).get("typeEngin").patchValue(engin[0].typeEngin);
        FGEngins.at(index).get("capacite").patchValue(engin[0].capacite);
        FGEngins.at(index).get("compteur").patchValue(engin[0].compteur);
        FGEngins.at(index).get("famille").patchValue(engin[0].famille);
        FGEngins.at(index).get("dateDernierAffectation").patchValue(engin[0].dateDernierAffectation);



        this.addEngin(engin[0]);
        this.list_criteres?.forEach(critere_parent =>{
          this.addCriteres(critere_parent,engin[0].codeEngin)
          })




       }
       else{
            engin.forEach(eng => {
                this.addEngin(eng);
                this.list_criteres?.forEach(critere_parent =>{
                  this.addCriteres(critere_parent,eng.codeEngin)
                  })


              this.itemsEngins.push(this.fb.group({
                idEnginAffect: null,
                codeEngin: eng.codeEngin,
                typeEngin: eng.type,
                capacite: eng.capacite,
                compteur: eng.compteur,
                famille: eng.famille,
                dateDernierAffectation: eng.dateAffectation,
                idDemandeEngin: eng.idDemandeEngin


              }))

            });

           // this.affecter()
       }




    });
  }


  disable_inputs= 'false';
  page= 'false';
  confirmer = '';
  listeEntite :any
  listePosts :any

  constructor(public datepipe: DatePipe,private _snackBar: MatSnackBar,private _Activatedroute:ActivatedRoute,public commandesService :CommandesService,private fb: FormBuilder,public dialog: MatDialog,private modalService: NgbModal,public router: Router) {






    this.commandesService.listeCriteres().subscribe((data:any)=>{

      this.list_criteres = data._embedded.criteres
      this.getDemande()
    })
    this.commandesService.listeEntite().subscribe((data:any)=>{
      this.listeEntite = data._embedded.entites
    })
    this.commandesService.listePosts().subscribe((data:any)=>{
      this.listePosts =data

    })


    this.commandesService.familles().subscribe((data:any)=>{

      this.list_famille = data._embedded.familles
      console.log(this.list_famille)
    })
    for (let i = 0; i < 35; i++) {
      this.posts_value.push(i);
    }
    this.FGCommande = new FormGroup({
      numBCI: new FormControl('16161'),
      entite: new FormControl('DTV'),
      dateDemande: new FormControl(),
      dateSortie: new FormControl(),
      shift: new FormControl('1'),
      poste: new FormControl('26'),
      statut:new FormControl(),
      familleDemandee:new FormControl([]),
      engins:new FormControl(),
    }
    )
    this.FGEnginDemande = new FormGroup({
      items: this.fb.array([])
    });
    this.FGEngins = new FormGroup({
      items: this.fb.array([])
    });


  }
  showObject(show){
      console.log(show)
  }



  ngOnInit(): void {

    this._Activatedroute
    .data
    .subscribe(v =>{
      console.log(v.disable_inputs)
      this.disable_inputs = v.disable_inputs
      this.page = v.page
      this.confirmer = v.confirmer

      if(this.page == "ncmd" || this.page == "mcmd"){
        this.DetailCMDisCollapsed = false
      }

    })


    this.createFGEnginCritere();





  }
  removeEngin(codeEngin){

    const dialogRef = this.dialog.open(PopUpConfirmComponent, {
      width: '1000px',
      data: {
        title : "Suppression d'engin " + codeEngin,
        message: "êtes-vous sûr de vouloir supprimer l'engin: " + codeEngin + " ?",
        btnOkText: "Supprimer",
        btnCancelText: "Annuler",
      },
    });

    dialogRef.afterClosed().subscribe(resp => {
      if(resp=="confirm"){
            const control = <FormArray>this.FGEnginCritere.get('engins');

            if(control.at(control.value.findIndex(engin => engin.codeEngin === codeEngin)).get("idDemandeEngin").value == null ){
              control.removeAt(control.value.findIndex(engin => engin.codeEngin === codeEngin) )
            }
            else{
              let enginAffecte = new EnginAffecte()
              enginAffecte.idDemandeEngin = control.at(control.value.findIndex(engin => engin.codeEngin === codeEngin)).get("idDemandeEngin").value
              this.commandesService.supenginaffect(enginAffecte).subscribe(data=>{
                control.removeAt(control.value.findIndex(engin => engin.codeEngin === codeEngin) )

              })
            }
      }

    })






    // <FormArray>this.FGEnginCritere.get('engins')["controls"].forEach(element_FG => {

    //   if(element_FG.get('codeEngin').value == codeEngin) {
    //     if(element_FG.get('idDemandeEngin').value == null){
    //       control.removeAt(control.value.findIndex(engin => engin.codeEngin === codeEngin) )

    //     }

    //   }
    // })

  }

  getDemande(){

    if(this.page == "engin"){

      this._Activatedroute.paramMap.subscribe(params => {
        if( params.get('codeEngin') != null)
        {
          this.commandesService.getDemandeByEngin(params.get('codeEngin')).subscribe((data:any)=>{
            this.getDataDemande(data)

            })

        }
      })
    }
    else{


          this._Activatedroute.paramMap.subscribe(params => {
            if( params.get('numbci') != null)
            {
              this.numBCI =params.get('numbci')
              this.commandesService.demande(params.get('numbci')).subscribe((data:any)=>{
                this.getDataDemande(data)


                })


            }

          })
    }

  }
  getDataDemande(data){

    this.FGCommande.setValue(data)


    var momentVariable = moment(this.FGCommande.get("dateSortie").value, 'yyyy-MM-DD');
    this.FGCommande.get("dateSortie").setValue(momentVariable.format('DD-MM-YYYY'));




    this.itemsFamilles = this.FGEnginDemande.get('items') as FormArray;

    data["familleDemandee"].forEach( famille => {
      this.itemsFamilles.push(this.fb.group({
        idFamille: famille.idFammile,
        nomFamille: famille.nomFamille,
        icon: famille.icon,
        quantite: famille.quantite,
        remarque: famille.remarque,
      }));
    });
    this.itemsEngins = this.FGEngins.get('items') as FormArray;

    let i = 0;

    data["engins"].forEach( engin => {
      let critere_eng = engin["critere"] as Array<critere>

      this.sortie = null;
      this.entree = null;

      critere_eng.forEach(critere=>{
        if(critere.etatSortie == "o" && this.sortie == null){
          this.sortie = "s"
        }
        if(critere.etatEntree == "o" && this.entree == null){
          this.entree = "e"
        }
      })

      console.log(engin)
      this.itemsEngins.push(this.fb.group({
        idEnginAffect: null,
        codeEngin: engin.codeEngin,
        typeEngin: engin.typeEngin,
        capacite: engin.capacite,
        compteur: engin.compteur,
        famille: engin.famille,
        dateDernierAffectation: engin.dateDernierAffectation,
        critere :this.fb.array(engin.critere),
        idDemandeEngin : engin.idDemandeEngin,

      }));
      engin.sortie = this.sortie
      engin.entree = this.entree

      this.addEngin(engin);

      if(critere_eng.length == 0){
        this.list_criteres?.forEach(critere_parent =>{
          this.addCriteres(critere_parent,engin.codeEngin)
          })
      }
      else{
        critere_eng.forEach(critere=>{


          this.addCriteres(critere,engin.codeEngin)
        })
      }



      i++


    });
    console.log(this.itemsEngins)
  }



  ajoutFamille(){

    if(this.formFamille.get('quantite').value == 0){
      this.openSnackBar("Veuillez saisir un nombre du quantite supérieur à 0")

    }
    else if(this.formFamille.get('idFamille').value != null && this.formFamille.get('idFamille').value != 0 ){

        console.log(this.formFamille.get('idFamille'))
        this.getFamilleById(this.formFamille.get('idFamille').value)

                this.itemsFamilles = this.FGEnginDemande.get('items') as FormArray;
                this.itemsFamilles.push(this.fb.group({
                  idFamille: this.formFamille.get('idFamille').value,
                  nomFamille: this.nomFamille,
                  quantite: this.formFamille.get('quantite').value,
                  remarque: this.formFamille.get('remarque').value,
                }));

        this.list_famille.forEach((element,index)=>{
          if(element.idFamille ==this.formFamille.get('idFamille').value )  this.list_famille.splice(index,1);;
      });
      this.list_famille= this.list_famille

      this.formFamille.reset()
      this.formFamille.get("quantite").setValue(1)

    }
    else{
      this.openSnackBar("Sélectionner une famille")
    }
  }
  ajouterDemande(){
    let Famille_demandee = ""
    let detailsDemandeList_= new Array<detailsDemandeList>()
    let FGEnginDemande = this.FGEnginDemande.get('items') as FormArray;
    for (let control of FGEnginDemande.controls) {
      let DemandeList = new detailsDemandeList()
      let famille_ = new famille()
      famille_.idFamille = control.value.idFamille
      DemandeList.famille = famille_
      DemandeList.quantite = control.value.quantite
      DemandeList.remarque = control.value.remarque

      detailsDemandeList_.push(DemandeList);
      Famille_demandee += " <br>" + " ("+control.value.quantite +") " +control.value.nomFamille
    }


    const dialogRef = this.dialog.open(PopUpConfirmComponent, {
      width: '1000px',
      data: {
        title : "Ajout demande d'engins " ,
        message: "êtes vous sur de vouloir créer une demande qui contient: " + Famille_demandee + " ",
        btnOkText: "Ajouter",
        btnCancelText: "Annuler",
      },
    });

    dialogRef.afterClosed().subscribe(resp => {
      if(resp=="confirm"){

            console.log(this.FGCommande.get("dateSortie").value)

            if(this.FGEnginDemande.controls.items.value.length > 0){

              let demande_= new demande(null)
              demande_.dateDemande=this.FGCommande.get("dateDemande")?.value
              demande_.dateSortie=this.convertDateToDB(this.FGCommande.get("dateSortie").value)
              demande_.shift=this.FGCommande.get("shift").value
              demande_.post= new post( Number(this.FGCommande.get("poste").value))
              demande_.numBCI= this.numBCI




              demande_.detailsDemandeList=detailsDemandeList_


              this.commandesService.ajouterDemande(demande_).subscribe((data:any)=>{
              console.log(data)
                this.router.navigate(['/commandes/enregistrement-sortie-entree/'+data.numBCI, ]);


              })
          }
          else{
            this.openSnackBar("Veuillez saisir au moins une famille")
          }

      }


    })


  }


  panne(engin){
    const dialogRef = this.dialog.open(PopUpReportPanneComponent, {
      width: '1000px',
      data: {engin: engin,numBCI:this.numBCI,criteres:this.list_criteres},
    });

    dialogRef.afterClosed().subscribe(engin => {
      this.openSnackBar("Panne ajouté avec succès")


    })

  }
  affecter(){
    let engins_demandee =  ""

    let FGEngins = this.FGEngins.get('items') as FormArray;
    for (let engin_ of FGEngins.controls) {
      engins_demandee += "<br>-" + engin_.value.codeEngin
    }

    const dialogRef = this.dialog.open(PopUpConfirmComponent, {
      width: '1000px',
      data: {
        title : "Reservation engins  " ,
        message: "êtes vous sur de vouloir réserver les engins suivants: " + engins_demandee + " ",
        btnOkText: "Réserver",
        btnCancelText: "Annuler",
      },
    });


    dialogRef.afterClosed().subscribe(resp => {

      if(resp=="confirm"){


    //numBCI
    let ListaffectationEngin_ = new Array<affectationEngin>();

    let FGEngins = this.FGEngins.get('items') as FormArray;
    for (let engin_ of FGEngins.controls) {
      let affectationEngin_ = new affectationEngin()
      affectationEngin_.engin = new engin(engin_.value.codeEngin)
      affectationEngin_.demande = new demande(this.numBCI)

      ListaffectationEngin_.push(affectationEngin_);
    }

    this.commandesService.ajouterAffecattion(ListaffectationEngin_).subscribe((data:any)=>{

              let FGEngins = this.FGEngins.get('items') as FormArray;
              let a = 0;
              FGEngins.value.forEach(fgengin => {
                console.log(fgengin)
                data.forEach(element => {
                    if(fgengin.codeEngin == element.codeEngin){
                      FGEngins.at(a).get("idEnginAffect").patchValue(element.idEnginAffect)
                      console.log(FGEngins.at(a).get("idEnginAffect").value)

                      this.list_criteres?.forEach(critere_parent =>{
                        this.addCriteres(critere_parent,a)
                        })
                    }


                });
                this.addEngin(fgengin)

                this.router.navigate(['/commandes/enregistrement-sortie-entree/'+this.numBCI, ]);


              });
              console.log(FGEngins);





            })

          }

    })


  }
  stopReservation = true;
  message_stop = ""

  checkAllCritereEntree(engin_){
   let  AllOk = true

    this.message_stop = ""
    let engin = ''
    let i = 0
    this.stopReservation = false
    const engins = this.FGEngins.get('items') as FormArray;

        for (let v = 0 ; v <  engins.length ; v++) {
          engin = <FormArray>this.FGEnginCritere.get('engins')["controls"][v]?.get("codeEngin").value + "";

          if(engin == engin_.codeEngin.value){

                  const engins_criteres = <FormArray>this.FGEnginCritere.get('engins')["controls"][v]?.get('critere');
                  for (let critere_ of engins_criteres?.controls) {
                    console.log(critere_.value)



                    if(critere_?.value?.etatEntree != "o" && critere_?.value?.etatEntree != "n" ){
                      console.log("aaaa")
                      AllOk = false
                      break
                      if(i == 0){
                        this.message_stop += "<br>"+ engin + ":"
                        i=1

                      }
                      this.stopReservation = true
                      this.message_stop +=  critere_.value.nomCritere + ", "

                    }


                  }
                  i = 0

          }


          }
          return AllOk
  }

  checkNonCritere(){
    this.message_stop = ""
    let engin = ''
    let i = 0
    this.stopReservation = false
    const engins = this.FGEngins.get('items') as FormArray;

        for (let v = 0 ; v <  engins.length ; v++) {
          engin = <FormArray>this.FGEnginCritere.get('engins')["controls"][v]?.get("codeEngin").value + "";

            const engins_criteres = <FormArray>this.FGEnginCritere.get('engins')["controls"][v]?.get('critere');
            let list_controleEngin = new Array<controleEngin>();
            for (let critere_ of engins_criteres?.controls) {

              let controleEngin_ = new controleEngin()
              controleEngin_.critere = new critere(critere_.value.idCritere)
              controleEngin_.etatEntree = critere_.value.etatEntree
              controleEngin_.etatSortie = critere_.value.etatSortie
              controleEngin_.obsSortie = critere_.value.observationSortie
              controleEngin_.osbEntree = critere_.value.observationEntree

              list_controleEngin.push(controleEngin_)
              if(critere_.value.etatSortie != "o"){

                if(i == 0){
                  this.message_stop += "<br>"+ engin + ":"
                   i=1

                }
                this.stopReservation = true
                this.message_stop +=  critere_.value.nomCritere + ", "

              }

            }
            i = 0
          }
  }
  ajouterControler(){

    let engins_demandee =  ""

    let FGEngins = this.FGEngins.get('items') as FormArray;
    for (let engin_ of FGEngins.controls) {
      engins_demandee += "<br>-" + engin_.value.codeEngin
    }

    const dialogRef = this.dialog.open(PopUpConfirmComponent, {
      width: '1000px',
      data: {
        title : "Reservation engins  " ,
        message: "êtes vous sur de vouloir réserver les engins suivants pour le BCI: " + this.numBCI + engins_demandee + " ",
        btnOkText: "Réserver",
        btnCancelText: "Annuler",
      },
    });


    dialogRef.afterClosed().subscribe(resp => {

      if(resp=="confirm"){



    let listEnginReserves = new Array<EnginReserves>()



    let listEngincritere = new Array<EnginAffecte>()
    this.itemsEngins = this.FGEngins.get('items') as FormArray;

    const engins = this.FGEngins.get('items') as FormArray;
    console.log(engins.length)

    for (let v = 0 ; v <  engins.length ; v++) {
      let enginReserves = new EnginReserves()



      let EnginAffecte_ = new  EnginAffecte();


       const engins_criteres = <FormArray>this.FGEnginCritere.get('engins')["controls"][v]?.get('critere');
      let list_controleEngin = new Array<controleEngin>();
      if(engins_criteres == undefined) break
      for (let critere_ of engins_criteres?.controls) {
        let controleEngin_ = new controleEngin()
        controleEngin_.critere = new critere(critere_.value.idCritere)
        controleEngin_.etatEntree = critere_.value.etatEntree
        controleEngin_.etatSortie = critere_.value.etatSortie
        controleEngin_.obsSortie = critere_.value.observationSortie
        controleEngin_.osbEntree = critere_.value.observationEntree

        list_controleEngin.push(controleEngin_)

      }
      enginReserves.engin =  new engin(engins.controls[v].get("codeEngin").value)
      enginReserves.controleEngin = list_controleEngin
      enginReserves.demande = new demande(this.numBCI)
      listEnginReserves.push(enginReserves)




      EnginAffecte_.controleEngin = list_controleEngin
      EnginAffecte_.idDemandeEngin = engins.controls[v].get("idDemandeEngin").value
      //EnginAffecte_.idDemandeEngin = <FormArray>this.FGEnginCritere.get('engins')["controls"][v].get("").value.
      listEngincritere.push(EnginAffecte_)

    }
          console.log(listEnginReserves)
              this.commandesService.reserver(listEnginReserves).subscribe((data :any)=>{
                data.forEach(element_ => {
                  <FormArray>this.FGEnginCritere.get('engins')["controls"].forEach(element_FG => {

                    if(element_FG.get('codeEngin').value == element_.codeEngin) {
                        const control = element_FG.get('idDemandeEngin');
                        control.patchValue(element_.idEnginAffect)

                    }
                    console.log(element_FG)


                  });


                })









              })


      }
    })


    // console.log(listEngincritere)
    // this.commandesService.ajouterControler(listEngincritere).subscribe(data=>{
    //   console.log('aa')
    //   this.router.navigate(['/commandes/confirmer-sortie/'+this.numBCI, ]);



    // })
  }
  removeFamille(i){

    this.list_famille.push()
    let itemsFamilles = this.FGEnginDemande.get('items') as FormArray;
    let famille = new listFamille()
    famille.idFamille = itemsFamilles.at(i).get("idFamille").value
    famille.nomFamille = itemsFamilles.at(i).get("nomFamille").value
    this.list_famille.push(famille)

    itemsFamilles.removeAt(i)


  }
  showOnConsole(value){

    console.log(value)
  }



}
