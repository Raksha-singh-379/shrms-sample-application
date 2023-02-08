import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectTeams } from '../project-teams.model';

@Component({
  selector: 'jhi-project-teams-detail',
  templateUrl: './project-teams-detail.component.html',
})
export class ProjectTeamsDetailComponent implements OnInit {
  projectTeams: IProjectTeams | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectTeams }) => {
      this.projectTeams = projectTeams;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
