import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProjectsFormService, ProjectsFormGroup } from './projects-form.service';
import { IProjects } from '../projects.model';
import { ProjectsService } from '../service/projects.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ProjectStatus } from 'app/entities/enumerations/project-status.model';

@Component({
  selector: 'jhi-projects-update',
  templateUrl: './projects-update.component.html',
})
export class ProjectsUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProjects | null = null;
  projectStatusValues = Object.keys(ProjectStatus);

  editForm: ProjectsFormGroup = this.projectsFormService.createProjectsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projectsService: ProjectsService,
    protected projectsFormService: ProjectsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projects }) => {
      this.projects = projects;
      if (projects) {
        this.updateForm(projects);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('shrmsApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projects = this.projectsFormService.getProjects(this.editForm);
    if (projects.id !== null) {
      this.subscribeToSaveResponse(this.projectsService.update(projects));
    } else {
      this.subscribeToSaveResponse(this.projectsService.create(projects));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjects>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(projects: IProjects): void {
    this.projects = projects;
    this.projectsFormService.resetForm(this.editForm, projects);
  }
}
