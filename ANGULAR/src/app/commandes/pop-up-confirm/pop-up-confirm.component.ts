import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-pop-up-confirm',
  templateUrl: './pop-up-confirm.component.html',
  styleUrls: ['./pop-up-confirm.component.scss']
})
export class PopUpConfirmComponent implements OnInit {

  title: string;
  message: string;
  btnOkText: string;
  btnCancelText: string;

  constructor(public dialogRef: MatDialogRef<PopUpConfirmComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {

    this.title = data.title;
    this.message= data.message;
    this.btnOkText= data.btnOkText;
    this.btnCancelText= data.btnCancelText;
  }

  ngOnInit(): void {
  }
  confirm(){
    this.dialogRef.close("confirm");
  }
  cancel(){
    this.dialogRef.close("cancel");

  }

}
