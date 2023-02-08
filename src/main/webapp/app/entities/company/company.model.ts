import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';

export interface ICompany {
  id: number;
  companyName?: string | null;
  contactPerson?: string | null;
  address?: string | null;
  postalCode?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  mobileNumber?: string | null;
  websiteUrl?: string | null;
  fax?: string | null;
  regNumber?: string | null;
  gstin?: string | null;
  pan?: string | null;
  tan?: string | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  deleted?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  address?: Pick<IAddress, 'id'> | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
