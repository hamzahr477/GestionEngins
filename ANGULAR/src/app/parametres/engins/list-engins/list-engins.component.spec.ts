import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEnginsComponent } from './list-engins.component';

describe('ListEnginsComponent', () => {
  let component: ListEnginsComponent;
  let fixture: ComponentFixture<ListEnginsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListEnginsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListEnginsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
