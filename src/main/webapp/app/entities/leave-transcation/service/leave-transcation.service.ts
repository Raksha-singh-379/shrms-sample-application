import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILeaveTranscation, NewLeaveTranscation } from '../leave-transcation.model';

export type PartialUpdateLeaveTranscation = Partial<ILeaveTranscation> & Pick<ILeaveTranscation, 'id'>;

type RestOf<T extends ILeaveTranscation | NewLeaveTranscation> = Omit<T, 'monthDate' | 'lastModified' | 'createdOn'> & {
  monthDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLeaveTranscation = RestOf<ILeaveTranscation>;

export type NewRestLeaveTranscation = RestOf<NewLeaveTranscation>;

export type PartialUpdateRestLeaveTranscation = RestOf<PartialUpdateLeaveTranscation>;

export type EntityResponseType = HttpResponse<ILeaveTranscation>;
export type EntityArrayResponseType = HttpResponse<ILeaveTranscation[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTranscationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/leave-transcations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(leaveTranscation: NewLeaveTranscation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveTranscation);
    return this.http
      .post<RestLeaveTranscation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(leaveTranscation: ILeaveTranscation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveTranscation);
    return this.http
      .put<RestLeaveTranscation>(`${this.resourceUrl}/${this.getLeaveTranscationIdentifier(leaveTranscation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(leaveTranscation: PartialUpdateLeaveTranscation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveTranscation);
    return this.http
      .patch<RestLeaveTranscation>(`${this.resourceUrl}/${this.getLeaveTranscationIdentifier(leaveTranscation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLeaveTranscation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLeaveTranscation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLeaveTranscationIdentifier(leaveTranscation: Pick<ILeaveTranscation, 'id'>): number {
    return leaveTranscation.id;
  }

  compareLeaveTranscation(o1: Pick<ILeaveTranscation, 'id'> | null, o2: Pick<ILeaveTranscation, 'id'> | null): boolean {
    return o1 && o2 ? this.getLeaveTranscationIdentifier(o1) === this.getLeaveTranscationIdentifier(o2) : o1 === o2;
  }

  addLeaveTranscationToCollectionIfMissing<Type extends Pick<ILeaveTranscation, 'id'>>(
    leaveTranscationCollection: Type[],
    ...leaveTranscationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const leaveTranscations: Type[] = leaveTranscationsToCheck.filter(isPresent);
    if (leaveTranscations.length > 0) {
      const leaveTranscationCollectionIdentifiers = leaveTranscationCollection.map(
        leaveTranscationItem => this.getLeaveTranscationIdentifier(leaveTranscationItem)!
      );
      const leaveTranscationsToAdd = leaveTranscations.filter(leaveTranscationItem => {
        const leaveTranscationIdentifier = this.getLeaveTranscationIdentifier(leaveTranscationItem);
        if (leaveTranscationCollectionIdentifiers.includes(leaveTranscationIdentifier)) {
          return false;
        }
        leaveTranscationCollectionIdentifiers.push(leaveTranscationIdentifier);
        return true;
      });
      return [...leaveTranscationsToAdd, ...leaveTranscationCollection];
    }
    return leaveTranscationCollection;
  }

  protected convertDateFromClient<T extends ILeaveTranscation | NewLeaveTranscation | PartialUpdateLeaveTranscation>(
    leaveTranscation: T
  ): RestOf<T> {
    return {
      ...leaveTranscation,
      monthDate: leaveTranscation.monthDate?.toJSON() ?? null,
      lastModified: leaveTranscation.lastModified?.toJSON() ?? null,
      createdOn: leaveTranscation.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLeaveTranscation: RestLeaveTranscation): ILeaveTranscation {
    return {
      ...restLeaveTranscation,
      monthDate: restLeaveTranscation.monthDate ? dayjs(restLeaveTranscation.monthDate) : undefined,
      lastModified: restLeaveTranscation.lastModified ? dayjs(restLeaveTranscation.lastModified) : undefined,
      createdOn: restLeaveTranscation.createdOn ? dayjs(restLeaveTranscation.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLeaveTranscation>): HttpResponse<ILeaveTranscation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLeaveTranscation[]>): HttpResponse<ILeaveTranscation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
