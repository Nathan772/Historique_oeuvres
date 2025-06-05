import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimeMenuUserComponent } from './anime-menu-user.component';

describe('AnimeMenuUserComponent', () => {
  let component: AnimeMenuUserComponent;
  let fixture: ComponentFixture<AnimeMenuUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimeMenuUserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimeMenuUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
