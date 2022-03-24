import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpSortieImprimmerComponent } from './pop-up-sortie-imprimmer.component';

describe('PopUpSortieImprimmerComponent', () => {
  let component: PopUpSortieImprimmerComponent;
  let fixture: ComponentFixture<PopUpSortieImprimmerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpSortieImprimmerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpSortieImprimmerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
