import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeavePolicyDetailComponent } from './leave-policy-detail.component';

describe('LeavePolicy Management Detail Component', () => {
  let comp: LeavePolicyDetailComponent;
  let fixture: ComponentFixture<LeavePolicyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeavePolicyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ leavePolicy: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LeavePolicyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LeavePolicyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load leavePolicy on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.leavePolicy).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
