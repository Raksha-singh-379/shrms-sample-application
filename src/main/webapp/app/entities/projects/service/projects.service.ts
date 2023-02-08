import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjects, NewProjects } from '../projects.model';

export type PartialUpdateProjects = Partial<IProjects> & Pick<IProjects, 'id'>;

type RestOf<T extends IProjects | NewProjects> = Omit<T, 'startDate' | 'endDate' | 'deadLine' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  deadLine?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestProjects = RestOf<IProjects>;

export type NewRestProjects = RestOf<NewProjects>;

export type PartialUpdateRestProjects = RestOf<PartialUpdateProjects>;

export type EntityResponseType = HttpResponse<IProjects>;
export type EntityArrayResponseType = HttpResponse<IProjects[]>;

@Injectable({ providedIn: 'root' })
export class ProjectsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projects');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projects: NewProjects): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projects);
    return this.http
      .post<RestProjects>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(projects: IProjects): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projects);
    return this.http
      .put<RestProjects>(`${this.resourceUrl}/${this.getProjectsIdentifier(projects)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(projects: PartialUpdateProjects): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projects);
    return this.http
      .patch<RestProjects>(`${this.resourceUrl}/${this.getProjectsIdentifier(projects)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProjects>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProjects[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectsIdentifier(projects: Pick<IProjects, 'id'>): number {
    return projects.id;
  }

  compareProjects(o1: Pick<IProjects, 'id'> | null, o2: Pick<IProjects, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectsIdentifier(o1) === this.getProjectsIdentifier(o2) : o1 === o2;
  }

  addProjectsToCollectionIfMissing<Type extends Pick<IProjects, 'id'>>(
    projectsCollection: Type[],
    ...projectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projects: Type[] = projectsToCheck.filter(isPresent);
    if (projects.length > 0) {
      const projectsCollectionIdentifiers = projectsCollection.map(projectsItem => this.getProjectsIdentifier(projectsItem)!);
      const projectsToAdd = projects.filter(projectsItem => {
        const projectsIdentifier = this.getProjectsIdentifier(projectsItem);
        if (projectsCollectionIdentifiers.includes(projectsIdentifier)) {
          return false;
        }
        projectsCollectionIdentifiers.push(projectsIdentifier);
        return true;
      });
      return [...projectsToAdd, ...projectsCollection];
    }
    return projectsCollection;
  }

  protected convertDateFromClient<T extends IProjects | NewProjects | PartialUpdateProjects>(projects: T): RestOf<T> {
    return {
      ...projects,
      startDate: projects.startDate?.toJSON() ?? null,
      endDate: projects.endDate?.toJSON() ?? null,
      deadLine: projects.deadLine?.toJSON() ?? null,
      lastModified: projects.lastModified?.toJSON() ?? null,
      createdOn: projects.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProjects: RestProjects): IProjects {
    return {
      ...restProjects,
      startDate: restProjects.startDate ? dayjs(restProjects.startDate) : undefined,
      endDate: restProjects.endDate ? dayjs(restProjects.endDate) : undefined,
      deadLine: restProjects.deadLine ? dayjs(restProjects.deadLine) : undefined,
      lastModified: restProjects.lastModified ? dayjs(restProjects.lastModified) : undefined,
      createdOn: restProjects.createdOn ? dayjs(restProjects.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProjects>): HttpResponse<IProjects> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProjects[]>): HttpResponse<IProjects[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
