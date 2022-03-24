import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpReportPanneComponent } from './pop-up-report-panne.component';

describe('PopUpReportPanneComponent', () => {
  let component: PopUpReportPanneComponent;
  let fixture: ComponentFixture<PopUpReportPanneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpReportPanneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PopUpReportPanneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
