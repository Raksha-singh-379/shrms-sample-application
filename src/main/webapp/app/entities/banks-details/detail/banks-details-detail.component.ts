import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBanksDetails } from '../banks-details.model';

@Component({
  selector: 'jhi-banks-details-detail',
  templateUrl: './banks-details-detail.component.html',
})
export class BanksDetailsDetailComponent implements OnInit {
  banksDetails: IBanksDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banksDetails }) => {
      this.banksDetails = banksDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
