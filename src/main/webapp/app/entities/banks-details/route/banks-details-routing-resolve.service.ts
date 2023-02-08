import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBanksDetails } from '../banks-details.model';
import { BanksDetailsService } from '../service/banks-details.service';

@Injectable({ providedIn: 'root' })
export class BanksDetailsRoutingResolveService implements Resolve<IBanksDetails | null> {
  constructor(protected service: BanksDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBanksDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((banksDetails: HttpResponse<IBanksDetails>) => {
          if (banksDetails.body) {
            return of(banksDetails.body);
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
