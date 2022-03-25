import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { ParametresService } from '../parametre.service';
import { entite } from '../posts/list-posts/list-posts.component';


export class user {
  id:string ;
  nom:string
   prenom:string
   matricule:string
    email:string
  password:string
   type:string
   entite:string
  constructor(id ,nom, prenom, matricule, email, password, type, id_entite){
    this.id = id
    this.nom = nom
    this.prenom =prenom
    this.matricule = matricule
    this.email = email
    this.password = password
    this.type = type
    this.entite = environment.url_prod+"/entites/"+id_entite
  }
}
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})

export class UserComponent implements OnInit {
  registerForm: FormGroup;
    submitted = false;


  listeEnt: any;
  myText:any = {  id:'' ,nom:'', prenom:'', matricule:'', email:'', password:'', type:'', id_entite:null}
  Rep=false;
  listeUser: any;

  constructor(public parametresService :ParametresService) { }


  getUser(){

    this.parametresService.listeUser().subscribe((data:any)=>{

      this.listeUser = data;
      console.log(this.listeUser)

    })
   }

   //Methode Post
   PostUser()
   {

    let user_ = new user(this.myText.id , this.myText.nom ,this.myText.prenom ,this.myText.matricule ,this.myText.email ,this.myText.password ,this.myText.type ,this.myText.id_entite )
    this.parametresService.addUser(user_).subscribe((valeur)=> {
       this.listeUser=[valeur, ...this.listeUser]
     this.handleClear();
     this.getUser();

   }

     )

   }

 ngOnInit(): void {
   this.getUser();
   this.listeEntit();

  }


  deleteUser(id: number)
  {

  this.parametresService.deleteUser(id).subscribe(()=>{
  this.listeUser.forEach((element,index)=>{
 if(element.id==id) this.listeUser.splice(index,1); });
 console.log("Delete successful")
  }, error => {
   console.error('There was an error!', error);
});
}
  editUser(valeur: any)
  {
   this.myText=valeur;
   this.Rep=true;
 }

  PutUser()
  {
    let tmp =  this.myText
    console.log(this.myText)
    tmp.entite=new entite(+this.myText.id_entite);
    this.parametresService.PutUser(tmp).subscribe((res)=>
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
        nom:' ',
        prenom:' ',
        matricule:' ',
        email:' ',
        password:' ',
        type:' ',
        id_entite:' '

    }
  }

  listeEntit(){
    this. parametresService.listeEntite().subscribe((data : any)=>{
       this.listeEnt = data._embedded.entites
       console.log(this.listeEnt)

     })
    }

 /////////////////////////////////////////////////////////////////////////////////////////////////////////////
 POSTS: any;
 page: number = 1;
 count: number = 0;
 tableSize: number = 7;
 tableSizes: any = [3, 6, 9, 12];

 fetchPosts(): void {
   this. parametresService.listeEngins().subscribe((data:any)=>{
     this.listeUser = data._embedded.users;
     console.log(this.listeUser)

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
