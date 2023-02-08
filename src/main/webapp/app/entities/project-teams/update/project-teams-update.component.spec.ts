import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectTeamsFormService } from './project-teams-form.service';
import { ProjectTeamsService } from '../service/project-teams.service';
import { IProjectTeams } from '../project-teams.model';

import { ProjectTeamsUpdateComponent } from './project-teams-update.component';

describe('ProjectTeams Management Update Component', () => {
  let comp: ProjectTeamsUpdateComponent;
  let fixture: ComponentFixture<ProjectTeamsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectTeamsFormService: ProjectTeamsFormService;
  let projectTeamsService: ProjectTeamsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectTeamsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjectTeamsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectTeamsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectTeamsFormService = TestBed.inject(ProjectTeamsFormService);
    projectTeamsService = TestBed.inject(ProjectTeamsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projectTeams: IProjectTeams = { id: 456 };

      activatedRoute.data = of({ projectTeams });
      comp.ngOnInit();

      expect(comp.projectTeams).toEqual(projectTeams);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectTeams>>();
      const projectTeams = { id: 123 };
      jest.spyOn(projectTeamsFormService, 'getProjectTeams').mockReturnValue(projectTeams);
      jest.spyOn(projectTeamsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTeams });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectTeams }));
      saveSubject.complete();

      // THEN
      expect(projectTeamsFormService.getProjectTeams).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectTeamsService.update).toHaveBeenCalledWith(expect.objectContaining(projectTeams));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectTeams>>();
      const projectTeams = { id: 123 };
      jest.spyOn(projectTeamsFormService, 'getProjectTeams').mockReturnValue({ id: null });
      jest.spyOn(projectTeamsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTeams: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projectTeams }));
      saveSubject.complete();

      // THEN
      expect(projectTeamsFormService.getProjectTeams).toHaveBeenCalled();
      expect(projectTeamsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjectTeams>>();
      const projectTeams = { id: 123 };
      jest.spyOn(projectTeamsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projectTeams });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectTeamsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
