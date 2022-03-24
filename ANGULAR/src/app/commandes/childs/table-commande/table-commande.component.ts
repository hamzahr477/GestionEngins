import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-table-commande',
  templateUrl: './table-commande.component.html',
  styleUrls: ['./table-commande.component.scss']
})
export class TableCommandeComponent implements OnInit {

  @Input() status: string;
  @Input() title: string;
  @Input() data: any;


  constructor() {
    console.log(this.data)
  }

  ngOnInit(): void {
  }

}
