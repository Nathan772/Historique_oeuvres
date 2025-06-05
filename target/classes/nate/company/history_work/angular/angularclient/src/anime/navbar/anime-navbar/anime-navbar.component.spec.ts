import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimeNavbarComponent } from './anime-navbar.component';

describe('AnimeNavbarComponent', () => {
  let component: AnimeNavbarComponent;
  let fixture: ComponentFixture<AnimeNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimeNavbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimeNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
