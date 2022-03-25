import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommandesService } from './../commandes.service';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { PopUpConfirmComponent } from '../pop-up-confirm/pop-up-confirm.component';

interface CriteresInterface {
  idCritere:string
  nomCritere: string;
  observation: string;
  panne: boolean;
}
class pannes {
  engin:engin
  currentDemande:currentDemande
  detailsPanneList:Array<detailsPanneList>

}
class engin{
  codeEngin:string
  constructor(codeEngin){
    this.codeEngin = codeEngin
  }
}
class currentDemande{
  numBCI:string

  constructor(numBCI){
    this.numBCI = numBCI
  }

}

class detailsPanneList{
  critere:critere
  observation:string

  constructor(critere,observation){
    this.critere = critere
    this.observation = observation
  }

}
class critere{
  idCritere:number

  constructor(idCritere){
    this.idCritere = idCritere
  }

}

@Component({
  selector: 'app-pop-up-report-panne',
  templateUrl: './pop-up-report-panne.component.html',
  styleUrls: ['./pop-up-report-panne.component.scss']
})
export class PopUpReportPanneComponent implements OnInit {
  FGCriteres:FormGroup
  itemsCriteres: FormArray

  result: {
    selectedCritere: CriteresInterface[];
  } =
  { selectedCritere: [] };




  constructor(public dialog: MatDialog,private fb: FormBuilder,public commandesService :CommandesService, public dialogRef: MatDialogRef<PopUpReportPanneComponent>,@Inject(MAT_DIALOG_DATA) public data: any) {


    this.FGCriteres = new FormGroup({
      items: this.fb.array([])
    });
    console.log(data)
    this.itemsCriteres = this.FGCriteres.get('items') as FormArray;
    data.criteres.forEach(element => {
        this.itemsCriteres.push(this.fb.group({
          idCritere:new FormControl(element.idCritere) ,
          nomCritere: new FormControl(element.nomCritere) ,
          panne:new FormControl(false),
          observation:new FormControl(""),

        }));
    });




   }

  ngOnInit(): void {

  }

  report(){

    const dialogRef = this.dialog.open(PopUpConfirmComponent, {
      width: '1000px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(resp => {
      if(resp=="confirm"){
            const { value } = this.FGCriteres;
            // get selected fruit from FormGroup value
            const selectedCritere = value?.items?.filter((f: CriteresInterface) => f.panne) || [];
              this.result = {selectedCritere}


              console.log(this.result)
              let ListdetailsPanneList = new Array<detailsPanneList>()
              let pannes_ = new pannes();
              pannes_.engin = new engin(this.data.engin)
              pannes_.currentDemande = new currentDemande(this.data.numBCI)
              selectedCritere.forEach(element => {
                let detailsPanneList_ = new detailsPanneList(new critere(element.idCritere),element.observation)
                ListdetailsPanneList.push(detailsPanneList_)

              });
              pannes_.detailsPanneList = ListdetailsPanneList

              this.commandesService.addPagne(pannes_).subscribe(data=>{
                console.log(data)
                this.dialogRef.close("reported")

              })

              console.log(pannes_)

      }
   })



  }
  getCriters(){
    return this.FGCriteres.controls.items["controls"]
  }
}
