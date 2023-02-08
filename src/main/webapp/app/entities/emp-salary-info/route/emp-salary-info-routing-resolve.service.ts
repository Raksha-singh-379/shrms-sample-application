import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { EmpSalaryInfoService } from '../service/emp-salary-info.service';

@Injectable({ providedIn: 'root' })
export class EmpSalaryInfoRoutingResolveService implements Resolve<IEmpSalaryInfo | null> {
  constructor(protected service: EmpSalaryInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpSalaryInfo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((empSalaryInfo: HttpResponse<IEmpSalaryInfo>) => {
          if (empSalaryInfo.body) {
            return of(empSalaryInfo.body);
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
