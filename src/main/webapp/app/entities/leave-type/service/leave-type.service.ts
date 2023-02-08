import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILeaveType, NewLeaveType } from '../leave-type.model';

export type PartialUpdateLeaveType = Partial<ILeaveType> & Pick<ILeaveType, 'id'>;

type RestOf<T extends ILeaveType | NewLeaveType> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLeaveType = RestOf<ILeaveType>;

export type NewRestLeaveType = RestOf<NewLeaveType>;

export type PartialUpdateRestLeaveType = RestOf<PartialUpdateLeaveType>;

export type EntityResponseType = HttpResponse<ILeaveType>;
export type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/leave-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(leaveType: NewLeaveType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveType);
    return this.http
      .post<RestLeaveType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(leaveType: ILeaveType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveType);
    return this.http
      .put<RestLeaveType>(`${this.resourceUrl}/${this.getLeaveTypeIdentifier(leaveType)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(leaveType: PartialUpdateLeaveType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveType);
    return this.http
      .patch<RestLeaveType>(`${this.resourceUrl}/${this.getLeaveTypeIdentifier(leaveType)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLeaveType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLeaveTypeIdentifier(leaveType: Pick<ILeaveType, 'id'>): number {
    return leaveType.id;
  }

  compareLeaveType(o1: Pick<ILeaveType, 'id'> | null, o2: Pick<ILeaveType, 'id'> | null): boolean {
    return o1 && o2 ? this.getLeaveTypeIdentifier(o1) === this.getLeaveTypeIdentifier(o2) : o1 === o2;
  }

  addLeaveTypeToCollectionIfMissing<Type extends Pick<ILeaveType, 'id'>>(
    leaveTypeCollection: Type[],
    ...leaveTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const leaveTypes: Type[] = leaveTypesToCheck.filter(isPresent);
    if (leaveTypes.length > 0) {
      const leaveTypeCollectionIdentifiers = leaveTypeCollection.map(leaveTypeItem => this.getLeaveTypeIdentifier(leaveTypeItem)!);
      const leaveTypesToAdd = leaveTypes.filter(leaveTypeItem => {
        const leaveTypeIdentifier = this.getLeaveTypeIdentifier(leaveTypeItem);
        if (leaveTypeCollectionIdentifiers.includes(leaveTypeIdentifier)) {
          return false;
        }
        leaveTypeCollectionIdentifiers.push(leaveTypeIdentifier);
        return true;
      });
      return [...leaveTypesToAdd, ...leaveTypeCollection];
    }
    return leaveTypeCollection;
  }

  protected convertDateFromClient<T extends ILeaveType | NewLeaveType | PartialUpdateLeaveType>(leaveType: T): RestOf<T> {
    return {
      ...leaveType,
      lastModified: leaveType.lastModified?.toJSON() ?? null,
      createdOn: leaveType.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLeaveType: RestLeaveType): ILeaveType {
    return {
      ...restLeaveType,
      lastModified: restLeaveType.lastModified ? dayjs(restLeaveType.lastModified) : undefined,
      createdOn: restLeaveType.createdOn ? dayjs(restLeaveType.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLeaveType>): HttpResponse<ILeaveType> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLeaveType[]>): HttpResponse<ILeaveType[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
