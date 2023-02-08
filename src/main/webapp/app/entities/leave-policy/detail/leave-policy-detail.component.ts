import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeavePolicy } from '../leave-policy.model';

@Component({
  selector: 'jhi-leave-policy-detail',
  templateUrl: './leave-policy-detail.component.html',
})
export class LeavePolicyDetailComponent implements OnInit {
  leavePolicy: ILeavePolicy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leavePolicy }) => {
      this.leavePolicy = leavePolicy;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
