import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICity, NewCity } from '../city.model';

export type PartialUpdateCity = Partial<ICity> & Pick<ICity, 'id'>;

type RestOf<T extends ICity | NewCity> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

export type RestCity = RestOf<ICity>;

export type NewRestCity = RestOf<NewCity>;

export type PartialUpdateRestCity = RestOf<PartialUpdateCity>;

export type EntityResponseType = HttpResponse<ICity>;
export type EntityArrayResponseType = HttpResponse<ICity[]>;

@Injectable({ providedIn: 'root' })
export class CityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(city: NewCity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(city);
    return this.http.post<RestCity>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(city: ICity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(city);
    return this.http
      .put<RestCity>(`${this.resourceUrl}/${this.getCityIdentifier(city)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(city: PartialUpdateCity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(city);
    return this.http
      .patch<RestCity>(`${this.resourceUrl}/${this.getCityIdentifier(city)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCityIdentifier(city: Pick<ICity, 'id'>): number {
    return city.id;
  }

  compareCity(o1: Pick<ICity, 'id'> | null, o2: Pick<ICity, 'id'> | null): boolean {
    return o1 && o2 ? this.getCityIdentifier(o1) === this.getCityIdentifier(o2) : o1 === o2;
  }

  addCityToCollectionIfMissing<Type extends Pick<ICity, 'id'>>(
    cityCollection: Type[],
    ...citiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cities: Type[] = citiesToCheck.filter(isPresent);
    if (cities.length > 0) {
      const cityCollectionIdentifiers = cityCollection.map(cityItem => this.getCityIdentifier(cityItem)!);
      const citiesToAdd = cities.filter(cityItem => {
        const cityIdentifier = this.getCityIdentifier(cityItem);
        if (cityCollectionIdentifiers.includes(cityIdentifier)) {
          return false;
        }
        cityCollectionIdentifiers.push(cityIdentifier);
        return true;
      });
      return [...citiesToAdd, ...cityCollection];
    }
    return cityCollection;
  }

  protected convertDateFromClient<T extends ICity | NewCity | PartialUpdateCity>(city: T): RestOf<T> {
    return {
      ...city,
      createdOn: city.createdOn?.toJSON() ?? null,
      lastModified: city.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCity: RestCity): ICity {
    return {
      ...restCity,
      createdOn: restCity.createdOn ? dayjs(restCity.createdOn) : undefined,
      lastModified: restCity.lastModified ? dayjs(restCity.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCity>): HttpResponse<ICity> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCity[]>): HttpResponse<ICity[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
