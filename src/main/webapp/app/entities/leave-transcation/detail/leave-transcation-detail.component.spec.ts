import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LeaveTranscationDetailComponent } from './leave-transcation-detail.component';

describe('LeaveTranscation Management Detail Component', () => {
  let comp: LeaveTranscationDetailComponent;
  let fixture: ComponentFixture<LeaveTranscationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeaveTranscationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ leaveTranscation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LeaveTranscationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LeaveTranscationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load leaveTranscation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.leaveTranscation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
