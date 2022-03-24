import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpRaisonSuppressionComponent } from './pop-up-raison-suppression.component';

describe('PopUpRaisonSuppressionComponent', () => {
  let component: PopUpRaisonSuppressionComponent;
  let fixture: ComponentFixture<PopUpRaisonSuppressionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpRaisonSuppressionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpRaisonSuppressionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
