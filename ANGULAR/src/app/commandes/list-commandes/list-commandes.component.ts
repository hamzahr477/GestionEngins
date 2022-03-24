import { FormBuilder, FormGroup } from '@angular/forms';
import { CommandesService } from './../commandes.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common'

@Component({
  selector: 'app-list-commandes',
  templateUrl: './list-commandes.component.html',
  styleUrls: ['./list-commandes.component.scss']
})
export class ListCommandesComponent implements OnInit {
  title="List commandes ";
  status = "";
  listeDemandes: any;

  type = ""




  constructor(private _Activatedroute:ActivatedRoute,public datepipe: DatePipe,public router: Router,public commandesService: CommandesService,private fb:FormBuilder) {

    this.commandesService.listeDemandes().subscribe((data)=>{
      this.listeDemandes = data
      if(this.type=="entregistrer"){
        this.listeDemandes  = this.listeDemandes.filter(demande => demande.statut == "Enregistée");
      }
      else{
        this.listeDemandes  = this.listeDemandes.filter(demande => demande.statut == "Verifiée");

      }
    })

    if(this.router.url == '/commandes/list-commandes'){
      this.status = "";
    }
    else if(this.router.url == '/commandes/list-commandes-enregistree'){
      this.status = "Enregistrée";
    }
    else if(this.router.url == '/commandes/list-commandes-activee'){
      this.status = "Activée";
    }
    else if(this.router.url == '/commandes/list-commandes-termine'){
      this.status = "Terminé";
    }
    else if(this.router.url == '/commandes/list-commandes-executee'){
      this.status = "Executée";
    }
  }

  ngOnInit(): void {


    this._Activatedroute
    .data
    .subscribe(v =>{
      console.log(v.disable_inputs)
      this.type = v.type
    });
  }
  convertDate(date){
    let latest_date =this.datepipe.transform(date, 'dd-MM-yyyy hh:mm');
    return latest_date
   }

}
