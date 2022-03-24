import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableCommandeComponent } from './table-commande.component';

describe('TableCommandeComponent', () => {
  let component: TableCommandeComponent;
  let fixture: ComponentFixture<TableCommandeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableCommandeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableCommandeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
