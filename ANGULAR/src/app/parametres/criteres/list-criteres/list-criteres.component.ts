import { Component, OnInit } from '@angular/core';
import { data } from 'jquery';
import { ParametresService } from '../../parametre.service';

@Component({
  selector: 'app-list-criteres',
  templateUrl: './list-criteres.component.html',
  styleUrls: ['./list-criteres.component.scss']
})


export class ListCriteresComponent implements OnInit {
  myText:any ={ nomCritere:' '}

  Rep=false;
  listeCritere: any;
  constructor(public parametresService :ParametresService) {  

    
    }
getcritere(){

  this.parametresService.listeCritere().subscribe((rest:any)=> { 
    this.listeCritere =rest._embedded.criteres;
      
       }
             );
}
 ngOnInit(): void {
this.getcritere();
 }
 
deletecritere(id: number){
  this.parametresService.deleteCritere(id).subscribe(()=>{


    this.listeCritere.forEach((element,index)=>{
      if(element.idCritere==id) this.listeCritere.splice(index,1);
   });


  console.log("Delete successful")  
   
}, error => {
 
  console.error('There was an error!', error);
});
}
 //Methode Post
 editCritere(valeur: any){

  this.myText=valeur;
  
  this.Rep=true;
  
  }
  handleClear(){

    this.myText=
    {
      nomCritere:' '
    }
  }
  PutCritere()
  { 
    this.parametresService.PutCritere(this.myText)
    .subscribe((res)=> {
    
  this.handleClear();
  this.Rep=false;

}
    )

  }

   //Methode Post
   postCriteres()
   { // mycommande objet 
    this.parametresService.addcritere(this.myText)
     .subscribe((valeur)=> {
     
     this.listeCritere=[valeur, ...this.listeCritere]
 this.handleClear();
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
      this.listeCritere = data["content"];
      console.log(this.listeCritere)
 
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
}