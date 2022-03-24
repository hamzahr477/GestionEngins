import { CommandesService } from './../commandes.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';



export interface PeriodicElement {
  position: number;
  famille: Object;
  codeEngin: string;
  typeEngin: string;
  capacite: string;
  compteur: string;
  matricule: string;
  dateDernierAffectation: string;
}



@Component({
  selector: 'app-pop-up-list-engin',
  templateUrl: './pop-up-list-engin.component.html',
  styleUrls: ['./pop-up-list-engin.component.scss']
})


export class PopUpListEnginComponent implements OnInit {


  displayedColumns: string[] = ['select','famille', 'codeEngin', 'type', 'capacite', 'compteur', 'matricule', 'dateAffectation'];
  dataSource = new MatTableDataSource<PeriodicElement>();
  selection = new SelectionModel<PeriodicElement>(true, []);

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;

    const numRows = this.dataSource.data.length;

    return numSelected === numRows;
  }

  selectHandler(row: PeriodicElement) {
    if (this.data.etat == "change") {

      if (!this.selection.isSelected(row)) {
        this.selection.clear();
      }
    }

    this.selection.toggle(row);
  }
  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: PeriodicElement): string {

    //console.log(row)
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;


  }



  constructor(public commandesService :CommandesService,
    public dialogRef: MatDialogRef<PopUpListEnginComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,) {
     // console.log(data)


  }

  ngOnInit(): void {

    this.commandesService.listeEnginsDisponible(this.data.idFamille).subscribe((data : any)=>{
      console.log(data)
      let newData :Array<any> = []

      let filtredArray :Array<any> = []
      console.log(this.data)

      data.forEach(element => {
        console.log(this.data.enginSelecter)
        if(this.data.enginSelecter == null){
          newData = data
        }
        else{

          let contain = false
              this.data.enginSelecter.forEach(elementselect => {
                console.log(elementselect)

                if(elementselect == element.codeEngin){

                  contain = true
                  return
                }


              });
              if(!contain) newData.push(element)

        }



      });

      console.log(filtredArray)
      this.dataSource.data = newData

    })
  }

  logSelection() {

    this.selection.selected.forEach(s => console.log(s.famille));
  }

  onClick(): void {
    console.log(this.selection.selected)
    this.dialogRef.close(this.selection.selected);
  }

}
