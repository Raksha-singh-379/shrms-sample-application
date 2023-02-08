import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkExperience } from '../work-experience.model';
import { WorkExperienceService } from '../service/work-experience.service';

@Injectable({ providedIn: 'root' })
export class WorkExperienceRoutingResolveService implements Resolve<IWorkExperience | null> {
  constructor(protected service: WorkExperienceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkExperience | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((workExperience: HttpResponse<IWorkExperience>) => {
          if (workExperience.body) {
            return of(workExperience.body);
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
