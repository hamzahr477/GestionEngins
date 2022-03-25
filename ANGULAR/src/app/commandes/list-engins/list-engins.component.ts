import { FormBuilder } from '@angular/forms';
import { CommandesService } from './../commandes.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-engins',
  templateUrl: './list-engins.component.html',
  styleUrls: ['./list-engins.component.scss']
})
export class ListEnginsComponent implements OnInit {

  listeEngins: any;
  type="null"

  constructor(public _Activatedroute :ActivatedRoute, public router: Router,public commandesService: CommandesService,private fb:FormBuilder) {





    if(this.router.url=='/commandes/list-engins-sortie'){
      this.commandesService.listeEnginsSortie().subscribe((data)=>{
        this.listeEngins = data
      })
    }
    if(this.router.url == '/commandes/list-engins-entree' ){

      this.commandesService.listeEnginsEntree().subscribe((data)=>{
        this.listeEngins = data
      })
    }


  }

  ngOnInit(): void {


  }

}
