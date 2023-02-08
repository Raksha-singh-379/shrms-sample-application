import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDesignation, NewDesignation } from '../designation.model';

export type PartialUpdateDesignation = Partial<IDesignation> & Pick<IDesignation, 'id'>;

type RestOf<T extends IDesignation | NewDesignation> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestDesignation = RestOf<IDesignation>;

export type NewRestDesignation = RestOf<NewDesignation>;

export type PartialUpdateRestDesignation = RestOf<PartialUpdateDesignation>;

export type EntityResponseType = HttpResponse<IDesignation>;
export type EntityArrayResponseType = HttpResponse<IDesignation[]>;

@Injectable({ providedIn: 'root' })
export class DesignationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/designations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(designation: NewDesignation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(designation);
    return this.http
      .post<RestDesignation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(designation: IDesignation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(designation);
    return this.http
      .put<RestDesignation>(`${this.resourceUrl}/${this.getDesignationIdentifier(designation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(designation: PartialUpdateDesignation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(designation);
    return this.http
      .patch<RestDesignation>(`${this.resourceUrl}/${this.getDesignationIdentifier(designation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDesignation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDesignation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDesignationIdentifier(designation: Pick<IDesignation, 'id'>): number {
    return designation.id;
  }

  compareDesignation(o1: Pick<IDesignation, 'id'> | null, o2: Pick<IDesignation, 'id'> | null): boolean {
    return o1 && o2 ? this.getDesignationIdentifier(o1) === this.getDesignationIdentifier(o2) : o1 === o2;
  }

  addDesignationToCollectionIfMissing<Type extends Pick<IDesignation, 'id'>>(
    designationCollection: Type[],
    ...designationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const designations: Type[] = designationsToCheck.filter(isPresent);
    if (designations.length > 0) {
      const designationCollectionIdentifiers = designationCollection.map(
        designationItem => this.getDesignationIdentifier(designationItem)!
      );
      const designationsToAdd = designations.filter(designationItem => {
        const designationIdentifier = this.getDesignationIdentifier(designationItem);
        if (designationCollectionIdentifiers.includes(designationIdentifier)) {
          return false;
        }
        designationCollectionIdentifiers.push(designationIdentifier);
        return true;
      });
      return [...designationsToAdd, ...designationCollection];
    }
    return designationCollection;
  }

  protected convertDateFromClient<T extends IDesignation | NewDesignation | PartialUpdateDesignation>(designation: T): RestOf<T> {
    return {
      ...designation,
      lastModified: designation.lastModified?.toJSON() ?? null,
      createdOn: designation.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDesignation: RestDesignation): IDesignation {
    return {
      ...restDesignation,
      lastModified: restDesignation.lastModified ? dayjs(restDesignation.lastModified) : undefined,
      createdOn: restDesignation.createdOn ? dayjs(restDesignation.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDesignation>): HttpResponse<IDesignation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDesignation[]>): HttpResponse<IDesignation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
