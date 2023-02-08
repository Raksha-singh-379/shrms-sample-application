import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SalarySettingsDetailComponent } from './salary-settings-detail.component';

describe('SalarySettings Management Detail Component', () => {
  let comp: SalarySettingsDetailComponent;
  let fixture: ComponentFixture<SalarySettingsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SalarySettingsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ salarySettings: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SalarySettingsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalarySettingsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salarySettings on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.salarySettings).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
