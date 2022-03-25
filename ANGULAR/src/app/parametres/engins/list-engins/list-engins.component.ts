import { Component, OnInit } from '@angular/core';
import { ExportExcelService } from 'src/app/services/excel/export-excel.service';
import { ParametresService } from '../../parametre.service';

export class famille{
  codeFamille:string
  nomFamille:string
  constructor(codeFamille,nomFamille){
    this.codeFamille = codeFamille
    this.nomFamille = nomFamille

  }
}

export class engins{

  compteur:number
  port:string
  nom_Equipement:string
  code_Equipement:string
  site:string
  codeEngin:string
  disponibiliteParck:string
  codeFamille:string
  nomfamille:string
  famille:famille
  constructor(){}

}


@Component({
  selector: 'app-list-engins',
  templateUrl: './list-engins.component.html',
  styleUrls: ['./list-engins.component.scss']
})


export class ListEnginsComponent implements OnInit {

  myText:any =
  {
    id:'',
  codeEngin:' ',
  capacite:' ',
  compteur:' ',
  port:' ',
  site:' ',
  nom_Equipement:' ',
  disponibiliteParck:' ',
  nomFamille:' '
}
  Rep=false;



  listeEngin: any;
  listeEngins: Array<engins>;
  listeFamil: any;
  selected=false
  listeEngine(){
    this. parametresService.listeEngins().subscribe((data : any)=>{
    this.listeEngin = data._embedded.engins

     })
    }
  deleteEngin(id: number)
  {

  this.parametresService.deleteEngin(id).subscribe(()=>{
  this.listeEngin.forEach((element,index)=>{
 if(element.id==id) this.listeEngin.splice(index,1); });
 console.log("Delete successful")
  }, error => {
   console.error('There was an error!', error);
});
}
 //Methode Post
 editEngin(valeur: any)
 {
  this.myText=valeur;
  this.Rep=true;
}

  handleClear()
  {
    this.myText=
    {
      codeEngin:' ',
      capacite:' ',
      compteur:' ',
      port:' ',
      site:' ',
      nom_Equipement:' ',
      disponibilite:' ',
      nomFamille:' '

    }
  }
  PutEngin()
  {
    this.parametresService.PutEngin(this.myText).subscribe((res)=>
     {
      this.handleClear();
       this.Rep=false;
       console.log("Update successful")
     }
    )

  }

   /////////////////////////////////////////////////////////////////////////////////////////////////////////////
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 7;
  tableSizes: any = [3, 6, 9, 12];

  fetchPosts(): void {
    this. parametresService.listeEngins().subscribe((data)=>{
      this.listeEngins = data as Array<engins>
      console.log(this.listeEngins)
      console.log(data as Array<engins>)
    });
  }
  onTableDataChange(event: any) {
    this.page = event;
    this.fetchPosts();
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.fetchPosts();
  }

  listeEquipements: any;
  constructor(public parametresService :ParametresService,private api: ExportExcelService,) {
 }

 listfamill(){
 this. parametresService.listeFamil().subscribe((data : any)=>{
    this.listeFamil = data._embedded.familles

  })
 }

  ngOnInit(): void {
    this.fetchPosts();
 this.listfamill();
 this.listeEngine();

  }


  syn(){

    let listeEngins = new Array<engins>()

     this. api.Get_Equipements().subscribe(( data: any  )=>{

       data.forEach(engin_row => {
        console.log(engin_row)

        let Engin_ = new engins()
        Engin_.codeEngin = engin_row.code_Equipement
        Engin_.nom_Equipement = engin_row.nom_Equipement
        Engin_.codeFamille = engin_row.code_Famille
        Engin_.nomfamille = engin_row.nom_Famille
        Engin_.compteur = +engin_row.compteur
         Engin_.famille =engin_row.code_Famille == null ? null :  new famille(engin_row.code_Famille,engin_row.nom_Famille)
        Engin_.port = engin_row.port
        Engin_.site = engin_row.site
        Engin_.disponibiliteParck = engin_row.disponibilite.toLowerCase() == "non disponible" ? 'indisponible' : engin_row.disponibilite.toLowerCase()
      listeEngins.push(Engin_)

      });
      this.parametresService.addEngins(listeEngins).subscribe(( data: any  )=>{
        console.log("200")

      })




  });





  }





}

