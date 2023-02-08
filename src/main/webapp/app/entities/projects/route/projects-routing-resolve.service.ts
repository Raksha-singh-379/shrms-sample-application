import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjects } from '../projects.model';
import { ProjectsService } from '../service/projects.service';

@Injectable({ providedIn: 'root' })
export class ProjectsRoutingResolveService implements Resolve<IProjects | null> {
  constructor(protected service: ProjectsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjects | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projects: HttpResponse<IProjects>) => {
          if (projects.body) {
            return of(projects.body);
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
