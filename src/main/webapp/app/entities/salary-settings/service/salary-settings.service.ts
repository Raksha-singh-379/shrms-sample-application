import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalarySettings, NewSalarySettings } from '../salary-settings.model';

export type PartialUpdateSalarySettings = Partial<ISalarySettings> & Pick<ISalarySettings, 'id'>;

type RestOf<T extends ISalarySettings | NewSalarySettings> = Omit<T, 'salaryFrom' | 'salaryTo' | 'lastModified' | 'createdOn'> & {
  salaryFrom?: string | null;
  salaryTo?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestSalarySettings = RestOf<ISalarySettings>;

export type NewRestSalarySettings = RestOf<NewSalarySettings>;

export type PartialUpdateRestSalarySettings = RestOf<PartialUpdateSalarySettings>;

export type EntityResponseType = HttpResponse<ISalarySettings>;
export type EntityArrayResponseType = HttpResponse<ISalarySettings[]>;

@Injectable({ providedIn: 'root' })
export class SalarySettingsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/salary-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(salarySettings: NewSalarySettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salarySettings);
    return this.http
      .post<RestSalarySettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(salarySettings: ISalarySettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salarySettings);
    return this.http
      .put<RestSalarySettings>(`${this.resourceUrl}/${this.getSalarySettingsIdentifier(salarySettings)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(salarySettings: PartialUpdateSalarySettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salarySettings);
    return this.http
      .patch<RestSalarySettings>(`${this.resourceUrl}/${this.getSalarySettingsIdentifier(salarySettings)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalarySettings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalarySettings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalarySettingsIdentifier(salarySettings: Pick<ISalarySettings, 'id'>): number {
    return salarySettings.id;
  }

  compareSalarySettings(o1: Pick<ISalarySettings, 'id'> | null, o2: Pick<ISalarySettings, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalarySettingsIdentifier(o1) === this.getSalarySettingsIdentifier(o2) : o1 === o2;
  }

  addSalarySettingsToCollectionIfMissing<Type extends Pick<ISalarySettings, 'id'>>(
    salarySettingsCollection: Type[],
    ...salarySettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salarySettings: Type[] = salarySettingsToCheck.filter(isPresent);
    if (salarySettings.length > 0) {
      const salarySettingsCollectionIdentifiers = salarySettingsCollection.map(
        salarySettingsItem => this.getSalarySettingsIdentifier(salarySettingsItem)!
      );
      const salarySettingsToAdd = salarySettings.filter(salarySettingsItem => {
        const salarySettingsIdentifier = this.getSalarySettingsIdentifier(salarySettingsItem);
        if (salarySettingsCollectionIdentifiers.includes(salarySettingsIdentifier)) {
          return false;
        }
        salarySettingsCollectionIdentifiers.push(salarySettingsIdentifier);
        return true;
      });
      return [...salarySettingsToAdd, ...salarySettingsCollection];
    }
    return salarySettingsCollection;
  }

  protected convertDateFromClient<T extends ISalarySettings | NewSalarySettings | PartialUpdateSalarySettings>(
    salarySettings: T
  ): RestOf<T> {
    return {
      ...salarySettings,
      salaryFrom: salarySettings.salaryFrom?.toJSON() ?? null,
      salaryTo: salarySettings.salaryTo?.toJSON() ?? null,
      lastModified: salarySettings.lastModified?.toJSON() ?? null,
      createdOn: salarySettings.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalarySettings: RestSalarySettings): ISalarySettings {
    return {
      ...restSalarySettings,
      salaryFrom: restSalarySettings.salaryFrom ? dayjs(restSalarySettings.salaryFrom) : undefined,
      salaryTo: restSalarySettings.salaryTo ? dayjs(restSalarySettings.salaryTo) : undefined,
      lastModified: restSalarySettings.lastModified ? dayjs(restSalarySettings.lastModified) : undefined,
      createdOn: restSalarySettings.createdOn ? dayjs(restSalarySettings.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalarySettings>): HttpResponse<ISalarySettings> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalarySettings[]>): HttpResponse<ISalarySettings[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
