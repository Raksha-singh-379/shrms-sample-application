import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveTranscation } from '../leave-transcation.model';

@Component({
  selector: 'jhi-leave-transcation-detail',
  templateUrl: './leave-transcation-detail.component.html',
})
export class LeaveTranscationDetailComponent implements OnInit {
  leaveTranscation: ILeaveTranscation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveTranscation }) => {
      this.leaveTranscation = leaveTranscation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
