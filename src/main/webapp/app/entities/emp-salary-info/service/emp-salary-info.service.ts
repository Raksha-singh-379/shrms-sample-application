import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpSalaryInfo, NewEmpSalaryInfo } from '../emp-salary-info.model';

export type PartialUpdateEmpSalaryInfo = Partial<IEmpSalaryInfo> & Pick<IEmpSalaryInfo, 'id'>;

type RestOf<T extends IEmpSalaryInfo | NewEmpSalaryInfo> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestEmpSalaryInfo = RestOf<IEmpSalaryInfo>;

export type NewRestEmpSalaryInfo = RestOf<NewEmpSalaryInfo>;

export type PartialUpdateRestEmpSalaryInfo = RestOf<PartialUpdateEmpSalaryInfo>;

export type EntityResponseType = HttpResponse<IEmpSalaryInfo>;
export type EntityArrayResponseType = HttpResponse<IEmpSalaryInfo[]>;

@Injectable({ providedIn: 'root' })
export class EmpSalaryInfoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emp-salary-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(empSalaryInfo: NewEmpSalaryInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empSalaryInfo);
    return this.http
      .post<RestEmpSalaryInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(empSalaryInfo: IEmpSalaryInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empSalaryInfo);
    return this.http
      .put<RestEmpSalaryInfo>(`${this.resourceUrl}/${this.getEmpSalaryInfoIdentifier(empSalaryInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(empSalaryInfo: PartialUpdateEmpSalaryInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empSalaryInfo);
    return this.http
      .patch<RestEmpSalaryInfo>(`${this.resourceUrl}/${this.getEmpSalaryInfoIdentifier(empSalaryInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmpSalaryInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmpSalaryInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpSalaryInfoIdentifier(empSalaryInfo: Pick<IEmpSalaryInfo, 'id'>): number {
    return empSalaryInfo.id;
  }

  compareEmpSalaryInfo(o1: Pick<IEmpSalaryInfo, 'id'> | null, o2: Pick<IEmpSalaryInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmpSalaryInfoIdentifier(o1) === this.getEmpSalaryInfoIdentifier(o2) : o1 === o2;
  }

  addEmpSalaryInfoToCollectionIfMissing<Type extends Pick<IEmpSalaryInfo, 'id'>>(
    empSalaryInfoCollection: Type[],
    ...empSalaryInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empSalaryInfos: Type[] = empSalaryInfosToCheck.filter(isPresent);
    if (empSalaryInfos.length > 0) {
      const empSalaryInfoCollectionIdentifiers = empSalaryInfoCollection.map(
        empSalaryInfoItem => this.getEmpSalaryInfoIdentifier(empSalaryInfoItem)!
      );
      const empSalaryInfosToAdd = empSalaryInfos.filter(empSalaryInfoItem => {
        const empSalaryInfoIdentifier = this.getEmpSalaryInfoIdentifier(empSalaryInfoItem);
        if (empSalaryInfoCollectionIdentifiers.includes(empSalaryInfoIdentifier)) {
          return false;
        }
        empSalaryInfoCollectionIdentifiers.push(empSalaryInfoIdentifier);
        return true;
      });
      return [...empSalaryInfosToAdd, ...empSalaryInfoCollection];
    }
    return empSalaryInfoCollection;
  }

  protected convertDateFromClient<T extends IEmpSalaryInfo | NewEmpSalaryInfo | PartialUpdateEmpSalaryInfo>(empSalaryInfo: T): RestOf<T> {
    return {
      ...empSalaryInfo,
      lastModified: empSalaryInfo.lastModified?.toJSON() ?? null,
      createdOn: empSalaryInfo.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEmpSalaryInfo: RestEmpSalaryInfo): IEmpSalaryInfo {
    return {
      ...restEmpSalaryInfo,
      lastModified: restEmpSalaryInfo.lastModified ? dayjs(restEmpSalaryInfo.lastModified) : undefined,
      createdOn: restEmpSalaryInfo.createdOn ? dayjs(restEmpSalaryInfo.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmpSalaryInfo>): HttpResponse<IEmpSalaryInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEmpSalaryInfo[]>): HttpResponse<IEmpSalaryInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
