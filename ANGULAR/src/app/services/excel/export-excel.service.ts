import { formatDate } from '@angular/common';
import { Injectable } from '@angular/core';

import * as logo from './mylogo.js';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from "rxjs"
import { Equipements } from 'src/app/model/Equipements.js';



@Injectable({
  providedIn: 'root'
})
export class ExportExcelService {


  api= "/api/"



  constructor( private http :HttpClient) { }

 Get_Equipements()
{
    let  urlApi =this.api
    return  this.http.get<Equipements>(urlApi);

}




}
