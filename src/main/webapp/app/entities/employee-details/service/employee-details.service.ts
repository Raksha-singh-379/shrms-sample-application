import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployeeDetails, NewEmployeeDetails } from '../employee-details.model';

export type PartialUpdateEmployeeDetails = Partial<IEmployeeDetails> & Pick<IEmployeeDetails, 'id'>;

type RestOf<T extends IEmployeeDetails | NewEmployeeDetails> = Omit<T, 'passportExpDate' | 'createdOn' | 'lastModified' | 'dob'> & {
  passportExpDate?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
  dob?: string | null;
};

export type RestEmployeeDetails = RestOf<IEmployeeDetails>;

export type NewRestEmployeeDetails = RestOf<NewEmployeeDetails>;

export type PartialUpdateRestEmployeeDetails = RestOf<PartialUpdateEmployeeDetails>;

export type EntityResponseType = HttpResponse<IEmployeeDetails>;
export type EntityArrayResponseType = HttpResponse<IEmployeeDetails[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employee-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employeeDetails: NewEmployeeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeDetails);
    return this.http
      .post<RestEmployeeDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(employeeDetails: IEmployeeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeDetails);
    return this.http
      .put<RestEmployeeDetails>(`${this.resourceUrl}/${this.getEmployeeDetailsIdentifier(employeeDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(employeeDetails: PartialUpdateEmployeeDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeDetails);
    return this.http
      .patch<RestEmployeeDetails>(`${this.resourceUrl}/${this.getEmployeeDetailsIdentifier(employeeDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmployeeDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmployeeDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmployeeDetailsIdentifier(employeeDetails: Pick<IEmployeeDetails, 'id'>): number {
    return employeeDetails.id;
  }

  compareEmployeeDetails(o1: Pick<IEmployeeDetails, 'id'> | null, o2: Pick<IEmployeeDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmployeeDetailsIdentifier(o1) === this.getEmployeeDetailsIdentifier(o2) : o1 === o2;
  }

  addEmployeeDetailsToCollectionIfMissing<Type extends Pick<IEmployeeDetails, 'id'>>(
    employeeDetailsCollection: Type[],
    ...employeeDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const employeeDetails: Type[] = employeeDetailsToCheck.filter(isPresent);
    if (employeeDetails.length > 0) {
      const employeeDetailsCollectionIdentifiers = employeeDetailsCollection.map(
        employeeDetailsItem => this.getEmployeeDetailsIdentifier(employeeDetailsItem)!
      );
      const employeeDetailsToAdd = employeeDetails.filter(employeeDetailsItem => {
        const employeeDetailsIdentifier = this.getEmployeeDetailsIdentifier(employeeDetailsItem);
        if (employeeDetailsCollectionIdentifiers.includes(employeeDetailsIdentifier)) {
          return false;
        }
        employeeDetailsCollectionIdentifiers.push(employeeDetailsIdentifier);
        return true;
      });
      return [...employeeDetailsToAdd, ...employeeDetailsCollection];
    }
    return employeeDetailsCollection;
  }

  protected convertDateFromClient<T extends IEmployeeDetails | NewEmployeeDetails | PartialUpdateEmployeeDetails>(
    employeeDetails: T
  ): RestOf<T> {
    return {
      ...employeeDetails,
      passportExpDate: employeeDetails.passportExpDate?.toJSON() ?? null,
      createdOn: employeeDetails.createdOn?.toJSON() ?? null,
      lastModified: employeeDetails.lastModified?.toJSON() ?? null,
      dob: employeeDetails.dob?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEmployeeDetails: RestEmployeeDetails): IEmployeeDetails {
    return {
      ...restEmployeeDetails,
      passportExpDate: restEmployeeDetails.passportExpDate ? dayjs(restEmployeeDetails.passportExpDate) : undefined,
      createdOn: restEmployeeDetails.createdOn ? dayjs(restEmployeeDetails.createdOn) : undefined,
      lastModified: restEmployeeDetails.lastModified ? dayjs(restEmployeeDetails.lastModified) : undefined,
      dob: restEmployeeDetails.dob ? dayjs(restEmployeeDetails.dob) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmployeeDetails>): HttpResponse<IEmployeeDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEmployeeDetails[]>): HttpResponse<IEmployeeDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
