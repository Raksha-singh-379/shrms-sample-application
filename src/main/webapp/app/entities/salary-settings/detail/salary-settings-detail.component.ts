import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalarySettings } from '../salary-settings.model';

@Component({
  selector: 'jhi-salary-settings-detail',
  templateUrl: './salary-settings-detail.component.html',
})
export class SalarySettingsDetailComponent implements OnInit {
  salarySettings: ISalarySettings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salarySettings }) => {
      this.salarySettings = salarySettings;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
