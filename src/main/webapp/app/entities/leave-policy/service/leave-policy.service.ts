import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILeavePolicy, NewLeavePolicy } from '../leave-policy.model';

export type PartialUpdateLeavePolicy = Partial<ILeavePolicy> & Pick<ILeavePolicy, 'id'>;

type RestOf<T extends ILeavePolicy | NewLeavePolicy> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLeavePolicy = RestOf<ILeavePolicy>;

export type NewRestLeavePolicy = RestOf<NewLeavePolicy>;

export type PartialUpdateRestLeavePolicy = RestOf<PartialUpdateLeavePolicy>;

export type EntityResponseType = HttpResponse<ILeavePolicy>;
export type EntityArrayResponseType = HttpResponse<ILeavePolicy[]>;

@Injectable({ providedIn: 'root' })
export class LeavePolicyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/leave-policies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(leavePolicy: NewLeavePolicy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leavePolicy);
    return this.http
      .post<RestLeavePolicy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(leavePolicy: ILeavePolicy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leavePolicy);
    return this.http
      .put<RestLeavePolicy>(`${this.resourceUrl}/${this.getLeavePolicyIdentifier(leavePolicy)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(leavePolicy: PartialUpdateLeavePolicy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leavePolicy);
    return this.http
      .patch<RestLeavePolicy>(`${this.resourceUrl}/${this.getLeavePolicyIdentifier(leavePolicy)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLeavePolicy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLeavePolicy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLeavePolicyIdentifier(leavePolicy: Pick<ILeavePolicy, 'id'>): number {
    return leavePolicy.id;
  }

  compareLeavePolicy(o1: Pick<ILeavePolicy, 'id'> | null, o2: Pick<ILeavePolicy, 'id'> | null): boolean {
    return o1 && o2 ? this.getLeavePolicyIdentifier(o1) === this.getLeavePolicyIdentifier(o2) : o1 === o2;
  }

  addLeavePolicyToCollectionIfMissing<Type extends Pick<ILeavePolicy, 'id'>>(
    leavePolicyCollection: Type[],
    ...leavePoliciesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const leavePolicies: Type[] = leavePoliciesToCheck.filter(isPresent);
    if (leavePolicies.length > 0) {
      const leavePolicyCollectionIdentifiers = leavePolicyCollection.map(
        leavePolicyItem => this.getLeavePolicyIdentifier(leavePolicyItem)!
      );
      const leavePoliciesToAdd = leavePolicies.filter(leavePolicyItem => {
        const leavePolicyIdentifier = this.getLeavePolicyIdentifier(leavePolicyItem);
        if (leavePolicyCollectionIdentifiers.includes(leavePolicyIdentifier)) {
          return false;
        }
        leavePolicyCollectionIdentifiers.push(leavePolicyIdentifier);
        return true;
      });
      return [...leavePoliciesToAdd, ...leavePolicyCollection];
    }
    return leavePolicyCollection;
  }

  protected convertDateFromClient<T extends ILeavePolicy | NewLeavePolicy | PartialUpdateLeavePolicy>(leavePolicy: T): RestOf<T> {
    return {
      ...leavePolicy,
      lastModified: leavePolicy.lastModified?.toJSON() ?? null,
      createdOn: leavePolicy.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLeavePolicy: RestLeavePolicy): ILeavePolicy {
    return {
      ...restLeavePolicy,
      lastModified: restLeavePolicy.lastModified ? dayjs(restLeavePolicy.lastModified) : undefined,
      createdOn: restLeavePolicy.createdOn ? dayjs(restLeavePolicy.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLeavePolicy>): HttpResponse<ILeavePolicy> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLeavePolicy[]>): HttpResponse<ILeavePolicy[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
