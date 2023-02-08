import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILeavePolicy } from '../leave-policy.model';
import { LeavePolicyService } from '../service/leave-policy.service';

@Injectable({ providedIn: 'root' })
export class LeavePolicyRoutingResolveService implements Resolve<ILeavePolicy | null> {
  constructor(protected service: LeavePolicyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeavePolicy | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((leavePolicy: HttpResponse<ILeavePolicy>) => {
          if (leavePolicy.body) {
            return of(leavePolicy.body);
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
