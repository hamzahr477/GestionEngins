import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListCriteresComponent } from './criteres/list-criteres/list-criteres.component';
import { ListEnginsComponent } from './engins/list-engins/list-engins.component';
import { ListEntitesComponent } from './entites/list-entites/list-entites.component';

import { ListFamillesComponent } from './familles/list-familles/list-familles.component';

import { ListPostsComponent } from './posts/list-posts/list-posts.component';

import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: 'list-engins', component: ListEnginsComponent },
  { path: 'list-criteres', component: ListCriteresComponent },
  { path: 'list-entites', component: ListEntitesComponent },
   { path: 'list-familles', component: ListFamillesComponent },
 { path: 'list-posts', component: ListPostsComponent },

  { path: 'user', component: UserComponent},
]
@NgModule({
  declarations: [ListCriteresComponent,  ListEnginsComponent, ListEntitesComponent,UserComponent,  ListFamillesComponent,  ListPostsComponent,  UserComponent],
  imports: [
    NgxPaginationModule,
    NgbModule,
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule
  ]
})
export class ParametresModule { }
