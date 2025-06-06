import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserConnexionComponent } from './user-connexion.component';

describe('UserConnexionComponent', () => {
  let component: UserConnexionComponent;
  let fixture: ComponentFixture<UserConnexionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserConnexionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
