import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WorkExperienceDetailComponent } from './work-experience-detail.component';

describe('WorkExperience Management Detail Component', () => {
  let comp: WorkExperienceDetailComponent;
  let fixture: ComponentFixture<WorkExperienceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkExperienceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ workExperience: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WorkExperienceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WorkExperienceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load workExperience on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.workExperience).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
