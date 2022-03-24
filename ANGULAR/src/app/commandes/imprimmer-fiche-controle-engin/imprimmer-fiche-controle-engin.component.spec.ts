import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImprimmerFicheControleEnginComponent } from './imprimmer-fiche-controle-engin.component';

describe('ImprimmerFicheControleEnginComponent', () => {
  let component: ImprimmerFicheControleEnginComponent;
  let fixture: ComponentFixture<ImprimmerFicheControleEnginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImprimmerFicheControleEnginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImprimmerFicheControleEnginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
