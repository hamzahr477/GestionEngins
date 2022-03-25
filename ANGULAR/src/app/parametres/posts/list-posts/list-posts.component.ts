import { Component, OnInit } from '@angular/core';
import { ParametresService } from '../../parametre.service';

export class entite {
  id:number
  entite : string

  constructor(id){
    this.id = id
  }
}
export class post {
  codePost:number
  entite : entite

  constructor(codePost,entite){
    this.codePost = codePost
    this.entite = entite
  }
}

@Component({
  selector: 'app-list-posts',
  templateUrl: './list-posts.component.html',
  styleUrls: ['./list-posts.component.scss']
})



export class ListPostsComponent implements OnInit {
  Listeposts: any;
  listeEnt:any;
  constructor(public parametresService :ParametresService) {

   }
   postesAll()
   {
    this.parametresService.listePosts().subscribe((data:any)=>{
      this.Listeposts =  data

      console.log(this.Listeposts)

    })
   }


   listepostes: any;
   myText:any = { codePost:' ', entite:' '}

   entite :any = { id:' ', entite:' '}

   Rep=false;


   listeEntit(){
    this.parametresService.listeEntite().subscribe((data : any)=>{
    this.listeEnt = data._embedded.entites

     })
    }


    //Methode Post
    PostPost()
    {

      let entite_ = new entite(+this.myText.entite)

      let post_ = new post(+this.myText.codePost,entite_)

     this.parametresService.addPost(post_).subscribe((valeur)=> {
        this.Listeposts=[valeur, ...this.Listeposts]
      this.handleClear();
    }

      )

    }

  ngOnInit(): void {
 this.postesAll();
 this.listeEntit();
   }


   deletePost(id: number)
   {

   this.parametresService.deletePost(id).subscribe(()=>{
   this.Listeposts.forEach((element,index)=>{
  if(element.id==id) this.Listeposts.splice(index,1); });
  console.log("Delete successful")
  this.fetchPosts()

   }, error => {
    console.error('There was an error!', error);
 });
 }
   editPost(valeur: any)
   {
    this.myText=valeur;
    this.Rep=true;
  }

  PutPost()
   {

    let entite_ = new entite(+this.myText.entite)

    let post_ = new post(+this.myText.codePost,entite_)

     this.parametresService.PutPost(post_).subscribe((res)=>
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
      codePost:' ', entite:' '

     }
   }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    POSTS: any;
    page: number = 1;
    count: number = 0;
    tableSize: number = 7;
    tableSizes: any = [3, 6, 9, 12];

    fetchPosts(): void {
      this. parametresService.listeEngins().subscribe((data)=>{
        this.Listeposts = data["content"];
        console.log(this.Listeposts)

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
