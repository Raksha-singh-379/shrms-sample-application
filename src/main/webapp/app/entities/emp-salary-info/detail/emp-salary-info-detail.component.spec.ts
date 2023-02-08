import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmpSalaryInfoDetailComponent } from './emp-salary-info-detail.component';

describe('EmpSalaryInfo Management Detail Component', () => {
  let comp: EmpSalaryInfoDetailComponent;
  let fixture: ComponentFixture<EmpSalaryInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpSalaryInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ empSalaryInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmpSalaryInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmpSalaryInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empSalaryInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.empSalaryInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
