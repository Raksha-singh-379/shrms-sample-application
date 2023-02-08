import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpSalaryInfo } from '../emp-salary-info.model';

@Component({
  selector: 'jhi-emp-salary-info-detail',
  templateUrl: './emp-salary-info-detail.component.html',
})
export class EmpSalaryInfoDetailComponent implements OnInit {
  empSalaryInfo: IEmpSalaryInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empSalaryInfo }) => {
      this.empSalaryInfo = empSalaryInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
