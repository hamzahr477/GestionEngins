import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NouvellePanneEnginComponent } from './nouvelle-panne-engin.component';

describe('NouvellePanneEnginComponent', () => {
  let component: NouvellePanneEnginComponent;
  let fixture: ComponentFixture<NouvellePanneEnginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NouvellePanneEnginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NouvellePanneEnginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
