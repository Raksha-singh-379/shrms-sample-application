import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILeaveTranscation } from '../leave-transcation.model';
import { LeaveTranscationService } from '../service/leave-transcation.service';

@Injectable({ providedIn: 'root' })
export class LeaveTranscationRoutingResolveService implements Resolve<ILeaveTranscation | null> {
  constructor(protected service: LeaveTranscationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeaveTranscation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((leaveTranscation: HttpResponse<ILeaveTranscation>) => {
          if (leaveTranscation.body) {
            return of(leaveTranscation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
