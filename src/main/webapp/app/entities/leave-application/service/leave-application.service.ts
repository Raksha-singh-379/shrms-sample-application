import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILeaveApplication, NewLeaveApplication } from '../leave-application.model';

export type PartialUpdateLeaveApplication = Partial<ILeaveApplication> & Pick<ILeaveApplication, 'id'>;

type RestOf<T extends ILeaveApplication | NewLeaveApplication> = Omit<T, 'formDate' | 'toDate' | 'lastModified' | 'createdOn'> & {
  formDate?: string | null;
  toDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLeaveApplication = RestOf<ILeaveApplication>;

export type NewRestLeaveApplication = RestOf<NewLeaveApplication>;

export type PartialUpdateRestLeaveApplication = RestOf<PartialUpdateLeaveApplication>;

export type EntityResponseType = HttpResponse<ILeaveApplication>;
export type EntityArrayResponseType = HttpResponse<ILeaveApplication[]>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/leave-applications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(leaveApplication: NewLeaveApplication): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveApplication);
    return this.http
      .post<RestLeaveApplication>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(leaveApplication: ILeaveApplication): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveApplication);
    return this.http
      .put<RestLeaveApplication>(`${this.resourceUrl}/${this.getLeaveApplicationIdentifier(leaveApplication)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(leaveApplication: PartialUpdateLeaveApplication): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveApplication);
    return this.http
      .patch<RestLeaveApplication>(`${this.resourceUrl}/${this.getLeaveApplicationIdentifier(leaveApplication)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLeaveApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLeaveApplication[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLeaveApplicationIdentifier(leaveApplication: Pick<ILeaveApplication, 'id'>): number {
    return leaveApplication.id;
  }

  compareLeaveApplication(o1: Pick<ILeaveApplication, 'id'> | null, o2: Pick<ILeaveApplication, 'id'> | null): boolean {
    return o1 && o2 ? this.getLeaveApplicationIdentifier(o1) === this.getLeaveApplicationIdentifier(o2) : o1 === o2;
  }

  addLeaveApplicationToCollectionIfMissing<Type extends Pick<ILeaveApplication, 'id'>>(
    leaveApplicationCollection: Type[],
    ...leaveApplicationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const leaveApplications: Type[] = leaveApplicationsToCheck.filter(isPresent);
    if (leaveApplications.length > 0) {
      const leaveApplicationCollectionIdentifiers = leaveApplicationCollection.map(
        leaveApplicationItem => this.getLeaveApplicationIdentifier(leaveApplicationItem)!
      );
      const leaveApplicationsToAdd = leaveApplications.filter(leaveApplicationItem => {
        const leaveApplicationIdentifier = this.getLeaveApplicationIdentifier(leaveApplicationItem);
        if (leaveApplicationCollectionIdentifiers.includes(leaveApplicationIdentifier)) {
          return false;
        }
        leaveApplicationCollectionIdentifiers.push(leaveApplicationIdentifier);
        return true;
      });
      return [...leaveApplicationsToAdd, ...leaveApplicationCollection];
    }
    return leaveApplicationCollection;
  }

  protected convertDateFromClient<T extends ILeaveApplication | NewLeaveApplication | PartialUpdateLeaveApplication>(
    leaveApplication: T
  ): RestOf<T> {
    return {
      ...leaveApplication,
      formDate: leaveApplication.formDate?.toJSON() ?? null,
      toDate: leaveApplication.toDate?.toJSON() ?? null,
      lastModified: leaveApplication.lastModified?.toJSON() ?? null,
      createdOn: leaveApplication.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLeaveApplication: RestLeaveApplication): ILeaveApplication {
    return {
      ...restLeaveApplication,
      formDate: restLeaveApplication.formDate ? dayjs(restLeaveApplication.formDate) : undefined,
      toDate: restLeaveApplication.toDate ? dayjs(restLeaveApplication.toDate) : undefined,
      lastModified: restLeaveApplication.lastModified ? dayjs(restLeaveApplication.lastModified) : undefined,
      createdOn: restLeaveApplication.createdOn ? dayjs(restLeaveApplication.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLeaveApplication>): HttpResponse<ILeaveApplication> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLeaveApplication[]>): HttpResponse<ILeaveApplication[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
