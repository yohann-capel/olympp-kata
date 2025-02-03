import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatementsDisplayerComponent } from './statements-displayer.component';

describe('StatementsDisplayerComponent', () => {
  let component: StatementsDisplayerComponent;
  let fixture: ComponentFixture<StatementsDisplayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatementsDisplayerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatementsDisplayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
