import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProjectTeamsFormService, ProjectTeamsFormGroup } from './project-teams-form.service';
import { IProjectTeams } from '../project-teams.model';
import { ProjectTeamsService } from '../service/project-teams.service';

@Component({
  selector: 'jhi-project-teams-update',
  templateUrl: './project-teams-update.component.html',
})
export class ProjectTeamsUpdateComponent implements OnInit {
  isSaving = false;
  projectTeams: IProjectTeams | null = null;

  editForm: ProjectTeamsFormGroup = this.projectTeamsFormService.createProjectTeamsFormGroup();

  constructor(
    protected projectTeamsService: ProjectTeamsService,
    protected projectTeamsFormService: ProjectTeamsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectTeams }) => {
      this.projectTeams = projectTeams;
      if (projectTeams) {
        this.updateForm(projectTeams);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectTeams = this.projectTeamsFormService.getProjectTeams(this.editForm);
    if (projectTeams.id !== null) {
      this.subscribeToSaveResponse(this.projectTeamsService.update(projectTeams));
    } else {
      this.subscribeToSaveResponse(this.projectTeamsService.create(projectTeams));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectTeams>>): void {
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

  protected updateForm(projectTeams: IProjectTeams): void {
    this.projectTeams = projectTeams;
    this.projectTeamsFormService.resetForm(this.editForm, projectTeams);
  }
}
