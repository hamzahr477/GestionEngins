import { Component, OnInit } from '@angular/core';
import { ParametresService } from '../../parametre.service';

@Component({
  selector: 'app-list-familles',
  templateUrl: './list-familles.component.html',
  styleUrls: ['./list-familles.component.scss']
})
export class ListFamillesComponent implements OnInit {
  listeFamil: any;
  constructor(public parametresService :ParametresService) {
   
   }

  ngOnInit(): void {
    this.getFamille();
  }
  listeFamille: any;
  myText:any = {  idFamille:' ' ,icon:' ', nomFamille:' '}
  Rep=false; 
 
  
  

  
  getFamille(){

    this.parametresService.listeFamil().subscribe((data:any)=>{

      this.listeFamil = data._embedded.familles;
      console.log(this.listeFamil)
   
    })
   }
    
   //Methode Post
   PostFamil()
   {
   
    this.parametresService.addFamil(this.myText).subscribe((valeur)=> {
       this.listeFamil=[valeur, ...this.listeFamil]
     this.handleClear();
   }

     )

   }

   deleteFamil(id: number)
  {

  this.parametresService.deleteFamil(id).subscribe(()=>{
  this.listeFamil.forEach((element,index)=>{
 if(element.id==id) this.listeFamil.splice(index,1); });
 console.log("Delete successful")  
  }, error => {
   console.error('There was an error!', error);
});
}
editFamil(valeur: any)
  {
   this.myText=valeur;
   this.Rep=true;
 }

 PutFamil()
  { 
    this.parametresService.PutFamil(this.myText).subscribe((res)=>
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
      idFamille:' ' ,icon:' ', nomFamille:' '
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
        this.listeFamil =  data._embedded.familles;
        console.log(this.listeFamil)
    
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
