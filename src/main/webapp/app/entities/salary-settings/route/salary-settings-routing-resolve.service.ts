import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalarySettings } from '../salary-settings.model';
import { SalarySettingsService } from '../service/salary-settings.service';

@Injectable({ providedIn: 'root' })
export class SalarySettingsRoutingResolveService implements Resolve<ISalarySettings | null> {
  constructor(protected service: SalarySettingsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalarySettings | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((salarySettings: HttpResponse<ISalarySettings>) => {
          if (salarySettings.body) {
            return of(salarySettings.body);
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
