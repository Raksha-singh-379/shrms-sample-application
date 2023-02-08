import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkExperience, NewWorkExperience } from '../work-experience.model';

export type PartialUpdateWorkExperience = Partial<IWorkExperience> & Pick<IWorkExperience, 'id'>;

type RestOf<T extends IWorkExperience | NewWorkExperience> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestWorkExperience = RestOf<IWorkExperience>;

export type NewRestWorkExperience = RestOf<NewWorkExperience>;

export type PartialUpdateRestWorkExperience = RestOf<PartialUpdateWorkExperience>;

export type EntityResponseType = HttpResponse<IWorkExperience>;
export type EntityArrayResponseType = HttpResponse<IWorkExperience[]>;

@Injectable({ providedIn: 'root' })
export class WorkExperienceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/work-experiences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(workExperience: NewWorkExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperience);
    return this.http
      .post<RestWorkExperience>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(workExperience: IWorkExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperience);
    return this.http
      .put<RestWorkExperience>(`${this.resourceUrl}/${this.getWorkExperienceIdentifier(workExperience)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workExperience: PartialUpdateWorkExperience): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workExperience);
    return this.http
      .patch<RestWorkExperience>(`${this.resourceUrl}/${this.getWorkExperienceIdentifier(workExperience)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkExperience[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkExperienceIdentifier(workExperience: Pick<IWorkExperience, 'id'>): number {
    return workExperience.id;
  }

  compareWorkExperience(o1: Pick<IWorkExperience, 'id'> | null, o2: Pick<IWorkExperience, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkExperienceIdentifier(o1) === this.getWorkExperienceIdentifier(o2) : o1 === o2;
  }

  addWorkExperienceToCollectionIfMissing<Type extends Pick<IWorkExperience, 'id'>>(
    workExperienceCollection: Type[],
    ...workExperiencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workExperiences: Type[] = workExperiencesToCheck.filter(isPresent);
    if (workExperiences.length > 0) {
      const workExperienceCollectionIdentifiers = workExperienceCollection.map(
        workExperienceItem => this.getWorkExperienceIdentifier(workExperienceItem)!
      );
      const workExperiencesToAdd = workExperiences.filter(workExperienceItem => {
        const workExperienceIdentifier = this.getWorkExperienceIdentifier(workExperienceItem);
        if (workExperienceCollectionIdentifiers.includes(workExperienceIdentifier)) {
          return false;
        }
        workExperienceCollectionIdentifiers.push(workExperienceIdentifier);
        return true;
      });
      return [...workExperiencesToAdd, ...workExperienceCollection];
    }
    return workExperienceCollection;
  }

  protected convertDateFromClient<T extends IWorkExperience | NewWorkExperience | PartialUpdateWorkExperience>(
    workExperience: T
  ): RestOf<T> {
    return {
      ...workExperience,
      startDate: workExperience.startDate?.toJSON() ?? null,
      endDate: workExperience.endDate?.toJSON() ?? null,
      lastModified: workExperience.lastModified?.toJSON() ?? null,
      createdOn: workExperience.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restWorkExperience: RestWorkExperience): IWorkExperience {
    return {
      ...restWorkExperience,
      startDate: restWorkExperience.startDate ? dayjs(restWorkExperience.startDate) : undefined,
      endDate: restWorkExperience.endDate ? dayjs(restWorkExperience.endDate) : undefined,
      lastModified: restWorkExperience.lastModified ? dayjs(restWorkExperience.lastModified) : undefined,
      createdOn: restWorkExperience.createdOn ? dayjs(restWorkExperience.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkExperience>): HttpResponse<IWorkExperience> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkExperience[]>): HttpResponse<IWorkExperience[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
