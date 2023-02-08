import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjectTeams } from '../project-teams.model';
import { ProjectTeamsService } from '../service/project-teams.service';

@Injectable({ providedIn: 'root' })
export class ProjectTeamsRoutingResolveService implements Resolve<IProjectTeams | null> {
  constructor(protected service: ProjectTeamsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectTeams | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projectTeams: HttpResponse<IProjectTeams>) => {
          if (projectTeams.body) {
            return of(projectTeams.body);
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
