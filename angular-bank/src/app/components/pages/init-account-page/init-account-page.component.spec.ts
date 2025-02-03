import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InitAccountPageComponent } from './init-account-page.component';

describe('InitAccountPageComponent', () => {
  let component: InitAccountPageComponent;
  let fixture: ComponentFixture<InitAccountPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InitAccountPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InitAccountPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
