import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListEntitesComponent } from './list-entites.component';

describe('ListEntitesComponent', () => {
  let component: ListEntitesComponent;
  let fixture: ComponentFixture<ListEntitesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListEntitesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListEntitesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
