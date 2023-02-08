import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkExperience } from '../work-experience.model';

@Component({
  selector: 'jhi-work-experience-detail',
  templateUrl: './work-experience-detail.component.html',
})
export class WorkExperienceDetailComponent implements OnInit {
  workExperience: IWorkExperience | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workExperience }) => {
      this.workExperience = workExperience;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
