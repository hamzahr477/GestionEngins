import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpListEnginComponent } from './pop-up-list-engin.component';

describe('PopUpListEnginComponent', () => {
  let component: PopUpListEnginComponent;
  let fixture: ComponentFixture<PopUpListEnginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpListEnginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpListEnginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
