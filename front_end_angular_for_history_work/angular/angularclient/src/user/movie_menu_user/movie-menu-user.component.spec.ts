import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieMenuUserComponent } from './movie-menu-user.component';

describe('MovieMenuUserComponent', () => {
  let component: MovieMenuUserComponent;
  let fixture: ComponentFixture<MovieMenuUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieMenuUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieMenuUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
