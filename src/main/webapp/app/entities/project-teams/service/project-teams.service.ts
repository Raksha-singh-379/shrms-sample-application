import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjectTeams, NewProjectTeams } from '../project-teams.model';

export type PartialUpdateProjectTeams = Partial<IProjectTeams> & Pick<IProjectTeams, 'id'>;

type RestOf<T extends IProjectTeams | NewProjectTeams> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestProjectTeams = RestOf<IProjectTeams>;

export type NewRestProjectTeams = RestOf<NewProjectTeams>;

export type PartialUpdateRestProjectTeams = RestOf<PartialUpdateProjectTeams>;

export type EntityResponseType = HttpResponse<IProjectTeams>;
export type EntityArrayResponseType = HttpResponse<IProjectTeams[]>;

@Injectable({ providedIn: 'root' })
export class ProjectTeamsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/project-teams');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projectTeams: NewProjectTeams): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectTeams);
    return this.http
      .post<RestProjectTeams>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(projectTeams: IProjectTeams): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectTeams);
    return this.http
      .put<RestProjectTeams>(`${this.resourceUrl}/${this.getProjectTeamsIdentifier(projectTeams)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(projectTeams: PartialUpdateProjectTeams): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projectTeams);
    return this.http
      .patch<RestProjectTeams>(`${this.resourceUrl}/${this.getProjectTeamsIdentifier(projectTeams)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProjectTeams>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProjectTeams[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectTeamsIdentifier(projectTeams: Pick<IProjectTeams, 'id'>): number {
    return projectTeams.id;
  }

  compareProjectTeams(o1: Pick<IProjectTeams, 'id'> | null, o2: Pick<IProjectTeams, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectTeamsIdentifier(o1) === this.getProjectTeamsIdentifier(o2) : o1 === o2;
  }

  addProjectTeamsToCollectionIfMissing<Type extends Pick<IProjectTeams, 'id'>>(
    projectTeamsCollection: Type[],
    ...projectTeamsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projectTeams: Type[] = projectTeamsToCheck.filter(isPresent);
    if (projectTeams.length > 0) {
      const projectTeamsCollectionIdentifiers = projectTeamsCollection.map(
        projectTeamsItem => this.getProjectTeamsIdentifier(projectTeamsItem)!
      );
      const projectTeamsToAdd = projectTeams.filter(projectTeamsItem => {
        const projectTeamsIdentifier = this.getProjectTeamsIdentifier(projectTeamsItem);
        if (projectTeamsCollectionIdentifiers.includes(projectTeamsIdentifier)) {
          return false;
        }
        projectTeamsCollectionIdentifiers.push(projectTeamsIdentifier);
        return true;
      });
      return [...projectTeamsToAdd, ...projectTeamsCollection];
    }
    return projectTeamsCollection;
  }

  protected convertDateFromClient<T extends IProjectTeams | NewProjectTeams | PartialUpdateProjectTeams>(projectTeams: T): RestOf<T> {
    return {
      ...projectTeams,
      lastModified: projectTeams.lastModified?.toJSON() ?? null,
      createdOn: projectTeams.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProjectTeams: RestProjectTeams): IProjectTeams {
    return {
      ...restProjectTeams,
      lastModified: restProjectTeams.lastModified ? dayjs(restProjectTeams.lastModified) : undefined,
      createdOn: restProjectTeams.createdOn ? dayjs(restProjectTeams.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProjectTeams>): HttpResponse<IProjectTeams> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProjectTeams[]>): HttpResponse<IProjectTeams[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
