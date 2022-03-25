import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NouvellePanneEnginComponent } from './nouvelle-panne-engin/nouvelle-panne-engin.component';
import { ListPanneComponent } from './list-panne/list-panne.component';


const routes: Routes = [
  { path: 'nouvelle-pane-engin', component: NouvellePanneEnginComponent },
  { path: 'list-pane-engin', component: ListPanneComponent }

]

@NgModule({
  declarations: [NouvellePanneEnginComponent, ListPanneComponent],
  imports: [
    CommonModule,
    NgbModule,
    RouterModule.forChild(routes)
  ]
})
export class PanesModule { }
