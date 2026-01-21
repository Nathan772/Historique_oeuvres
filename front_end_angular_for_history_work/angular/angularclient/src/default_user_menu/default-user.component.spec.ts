import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultUserComponent } from './default-user.component';

describe('DefaultUserComponent', () => {
  let component: DefaultUserComponent;
  let fixture: ComponentFixture<DefaultUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DefaultUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
