import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoDLPageComponent } from './video-dlpage.component';

describe('VideoDLPageComponent', () => {
  let component: VideoDLPageComponent;
  let fixture: ComponentFixture<VideoDLPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VideoDLPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VideoDLPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
