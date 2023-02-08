import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectTeamsDetailComponent } from './project-teams-detail.component';

describe('ProjectTeams Management Detail Component', () => {
  let comp: ProjectTeamsDetailComponent;
  let fixture: ComponentFixture<ProjectTeamsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectTeamsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projectTeams: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectTeamsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectTeamsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projectTeams on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projectTeams).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
