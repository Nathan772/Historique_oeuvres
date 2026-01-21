import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAnimeListComponent } from './user-anime-list.component';

describe('UserAnimeListComponent', () => {
  let component: UserAnimeListComponent;
  let fixture: ComponentFixture<UserAnimeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserAnimeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAnimeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
