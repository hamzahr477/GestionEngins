import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPanneComponent } from './list-panne.component';

describe('ListPanneComponent', () => {
  let component: ListPanneComponent;
  let fixture: ComponentFixture<ListPanneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPanneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPanneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
