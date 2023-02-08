import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILeaveType } from '../leave-type.model';
import { LeaveTypeService } from '../service/leave-type.service';

@Injectable({ providedIn: 'root' })
export class LeaveTypeRoutingResolveService implements Resolve<ILeaveType | null> {
  constructor(protected service: LeaveTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeaveType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((leaveType: HttpResponse<ILeaveType>) => {
          if (leaveType.body) {
            return of(leaveType.body);
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
