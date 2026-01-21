import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonFunctionalityComponentComponent } from './common-functionality-component.component';

describe('CommonFunctionalityComponentComponent', () => {
  let component: CommonFunctionalityComponentComponent;
  let fixture: ComponentFixture<CommonFunctionalityComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonFunctionalityComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommonFunctionalityComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


