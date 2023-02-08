import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectsFormService } from './projects-form.service';
import { ProjectsService } from '../service/projects.service';
import { IProjects } from '../projects.model';

import { ProjectsUpdateComponent } from './projects-update.component';

describe('Projects Management Update Component', () => {
  let comp: ProjectsUpdateComponent;
  let fixture: ComponentFixture<ProjectsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectsFormService: ProjectsFormService;
  let projectsService: ProjectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectsUpdateComponent],
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
      .overrideTemplate(ProjectsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectsFormService = TestBed.inject(ProjectsFormService);
    projectsService = TestBed.inject(ProjectsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projects: IProjects = { id: 456 };

      activatedRoute.data = of({ projects });
      comp.ngOnInit();

      expect(comp.projects).toEqual(projects);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjects>>();
      const projects = { id: 123 };
      jest.spyOn(projectsFormService, 'getProjects').mockReturnValue(projects);
      jest.spyOn(projectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projects }));
      saveSubject.complete();

      // THEN
      expect(projectsFormService.getProjects).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectsService.update).toHaveBeenCalledWith(expect.objectContaining(projects));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjects>>();
      const projects = { id: 123 };
      jest.spyOn(projectsFormService, 'getProjects').mockReturnValue({ id: null });
      jest.spyOn(projectsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projects: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projects }));
      saveSubject.complete();

      // THEN
      expect(projectsFormService.getProjects).toHaveBeenCalled();
      expect(projectsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjects>>();
      const projects = { id: 123 };
      jest.spyOn(projectsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projects });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
