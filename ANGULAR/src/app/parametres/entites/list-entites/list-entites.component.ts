import { Component, OnInit } from '@angular/core';
import { ParametresService } from '../../parametre.service';

@Component({
  selector: 'app-list-entites',
  templateUrl: './list-entites.component.html',
  styleUrls: ['./list-entites.component.scss']
})

export class ListEntitesComponent implements OnInit {
  myText:any = {  entite:' '}
  Rep=false; 
  listeEntites: any;

  constructor(public parametresService :ParametresService) {}
   
  getEntite(){

    this.parametresService.listeEntite().subscribe((data:any)=>{
   
      this.listeEntites = data._embedded.entites;


    
    })
   }
    
   //Methode Post
   postEntite()
   {
      // mycommande objet 
    this.parametresService.addEntite(this.myText).subscribe((valeur)=> {
     console.log(valeur)
     this.listeEntites=[valeur, ...this.listeEntites]
     this.handleClear();
   }

     )

   }

 ngOnInit(): void {
   this.getEntite();
  }
 

  deleteEntite(id: number)
  {

  this.parametresService.deleteEntite(id).subscribe(()=>{
  this.listeEntites.forEach((element,index)=>{
 if(element.id==id) this.listeEntites.splice(index,1); });
 console.log("Delete successful")  
  }, error => {
   console.error('There was an error!', error);
});
}
  editEntite(valeur: any)
  {
   this.myText=valeur;
   this.Rep=true;
 }

  PutEntite()
  { 
    this.parametresService.PutEntite(this.myText).subscribe((res)=>
     {
      this.handleClear();
       this.Rep=false;
     }
    )

  }
  handleClear()
  {
    this.myText=
    {
      entite:' '

    }
  }
 
  
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    POSTS: any;
    page: number = 1;
    count: number = 0;
    tableSize: number = 7;
    tableSizes: any = [3, 6, 9, 12];
   
    fetchPosts(): void {
      this. parametresService.listeEngins().subscribe((data:any)=>{
        this.listeEntites = data._embedded.entites;
        console.log(this.listeEntites)
    
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