import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoDLFormComponent } from './video-dl-form.component';

describe('VideoDLFormComponent', () => {
  let component: VideoDLFormComponent;
  let fixture: ComponentFixture<VideoDLFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VideoDLFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VideoDLFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
