import { PopUpReportPanneComponent } from './pop-up-report-panne/pop-up-report-panne.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TableCommandeComponent } from './childs/table-commande/table-commande.component';
import { ListCommandesComponent } from './list-commandes/list-commandes.component';
import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { RouterModule, Routes } from '@angular/router';
import { NouvelleCommandeComponent } from './nouvelle-commande/nouvelle-commande.component';
import { PopUpListEnginComponent } from './pop-up-list-engin/pop-up-list-engin.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListEnginsComponent } from './list-engins/list-engins.component';
import { EnginComponent } from './engin/engin.component';
import { PopUpSortieImprimmerComponent } from './pop-up-sortie-imprimmer/pop-up-sortie-imprimmer.component';
import {MatChipsModule} from '@angular/material/chips';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';

import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_FORMATS } from '@angular/material/core';
import { QRCodeModule } from 'angular2-qrcode';
import { ImprimmerFicheControleEnginComponent } from './imprimmer-fiche-controle-engin/imprimmer-fiche-controle-engin.component';
import { PopUpConfirmComponent } from './pop-up-confirm/pop-up-confirm.component';


export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
  },
};


const routes: Routes = [
  { path: 'list-commandes', component: ListCommandesComponent , data:{type:'entregistrer'}},
  { path: 'list-demandes', component: ListCommandesComponent, data:{type:'verifier'} },
  { path: 'list-commandes-enregistree', component: ListCommandesComponent },
  { path: 'list-commandes-enregistree', component: ListCommandesComponent },
  { path: 'list-engins-sortie', component: ListEnginsComponent },
  { path: 'list-engins-entree', component: ListEnginsComponent },
  { path: 'list-commandes-executee', component: ListCommandesComponent },
  { path: 'list-commandes', component: ListCommandesComponent },



  { path: 'nouvelle-commandes', component: NouvelleCommandeComponent ,data: {disable_inputs: 'false' , page:"ncmd"}},
  { path: 'engin/:codeEngin', component: NouvelleCommandeComponent ,data: {disable_inputs: 'true' , page:"engin"}},
  { path: 'modifier-commandes/:numbci', component: NouvelleCommandeComponent ,data: {disable_inputs: 'false' , page:"mcmd"}},
  { path: 'nouvelle-affectation/:numbci', component: NouvelleCommandeComponent,data: {disable_inputs: 'true' , page:"affectation"} },
  { path: 'enregistrement-sortie-entree/:numbci', component: NouvelleCommandeComponent ,data: {disable_inputs: 'true',page: 'se'}  },
  { path: 'confirmer-sortie/:numbci', component: NouvelleCommandeComponent ,data: {disable_inputs: 'true',page: 'se',confirmer:'confirmer-sortie'}  },

]

@NgModule({
  declarations: [
    ListCommandesComponent,
    TableCommandeComponent,
    NouvelleCommandeComponent,
    PopUpListEnginComponent,
    PopUpSortieImprimmerComponent,
    ListEnginsComponent,
    EnginComponent,
    PopUpReportPanneComponent,
    ImprimmerFicheControleEnginComponent,
    PopUpConfirmComponent,

  ],
  imports: [
    CommonModule,
    NgbModule,
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatChipsModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatIconModule,
    MatAutocompleteModule,
    MatSlideToggleModule,
    MatInputModule,
    MatSnackBarModule,
    MatSelectModule,
    MatNativeDateModule,
    MatDatepickerModule,
    QRCodeModule,
    RouterModule.forChild(routes)

  ],
  entryComponents: [
    PopUpListEnginComponent,
    PopUpSortieImprimmerComponent,
    PopUpReportPanneComponent,
    PopUpConfirmComponent,

  ],
  exports:[
    MatDialogModule

  ],
  providers: [
    DatePipe,
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class CommandesModule { }


