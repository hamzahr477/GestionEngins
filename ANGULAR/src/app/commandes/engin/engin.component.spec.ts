import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnginComponent } from './engin.component';

describe('EnginComponent', () => {
  let component: EnginComponent;
  let fixture: ComponentFixture<EnginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
