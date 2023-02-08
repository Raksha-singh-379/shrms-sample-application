import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBanksDetails, NewBanksDetails } from '../banks-details.model';

export type PartialUpdateBanksDetails = Partial<IBanksDetails> & Pick<IBanksDetails, 'id'>;

type RestOf<T extends IBanksDetails | NewBanksDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestBanksDetails = RestOf<IBanksDetails>;

export type NewRestBanksDetails = RestOf<NewBanksDetails>;

export type PartialUpdateRestBanksDetails = RestOf<PartialUpdateBanksDetails>;

export type EntityResponseType = HttpResponse<IBanksDetails>;
export type EntityArrayResponseType = HttpResponse<IBanksDetails[]>;

@Injectable({ providedIn: 'root' })
export class BanksDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/banks-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(banksDetails: NewBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(banksDetails);
    return this.http
      .post<RestBanksDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(banksDetails: IBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(banksDetails);
    return this.http
      .put<RestBanksDetails>(`${this.resourceUrl}/${this.getBanksDetailsIdentifier(banksDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(banksDetails: PartialUpdateBanksDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(banksDetails);
    return this.http
      .patch<RestBanksDetails>(`${this.resourceUrl}/${this.getBanksDetailsIdentifier(banksDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBanksDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBanksDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBanksDetailsIdentifier(banksDetails: Pick<IBanksDetails, 'id'>): number {
    return banksDetails.id;
  }

  compareBanksDetails(o1: Pick<IBanksDetails, 'id'> | null, o2: Pick<IBanksDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getBanksDetailsIdentifier(o1) === this.getBanksDetailsIdentifier(o2) : o1 === o2;
  }

  addBanksDetailsToCollectionIfMissing<Type extends Pick<IBanksDetails, 'id'>>(
    banksDetailsCollection: Type[],
    ...banksDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const banksDetails: Type[] = banksDetailsToCheck.filter(isPresent);
    if (banksDetails.length > 0) {
      const banksDetailsCollectionIdentifiers = banksDetailsCollection.map(
        banksDetailsItem => this.getBanksDetailsIdentifier(banksDetailsItem)!
      );
      const banksDetailsToAdd = banksDetails.filter(banksDetailsItem => {
        const banksDetailsIdentifier = this.getBanksDetailsIdentifier(banksDetailsItem);
        if (banksDetailsCollectionIdentifiers.includes(banksDetailsIdentifier)) {
          return false;
        }
        banksDetailsCollectionIdentifiers.push(banksDetailsIdentifier);
        return true;
      });
      return [...banksDetailsToAdd, ...banksDetailsCollection];
    }
    return banksDetailsCollection;
  }

  protected convertDateFromClient<T extends IBanksDetails | NewBanksDetails | PartialUpdateBanksDetails>(banksDetails: T): RestOf<T> {
    return {
      ...banksDetails,
      lastModified: banksDetails.lastModified?.toJSON() ?? null,
      createdOn: banksDetails.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restBanksDetails: RestBanksDetails): IBanksDetails {
    return {
      ...restBanksDetails,
      lastModified: restBanksDetails.lastModified ? dayjs(restBanksDetails.lastModified) : undefined,
      createdOn: restBanksDetails.createdOn ? dayjs(restBanksDetails.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBanksDetails>): HttpResponse<IBanksDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBanksDetails[]>): HttpResponse<IBanksDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
