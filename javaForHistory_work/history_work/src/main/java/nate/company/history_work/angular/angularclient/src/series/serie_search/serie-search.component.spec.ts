import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieSearchComponent } from './serie-search.component';

describe('SerieSearchComponent', () => {
  let component: SerieSearchComponent;
  let fixture: ComponentFixture<SerieSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SerieSearchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SerieSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
