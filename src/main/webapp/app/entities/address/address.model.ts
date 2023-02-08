import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { ICity } from 'app/entities/city/city.model';
import { AddressType } from 'app/entities/enumerations/address-type.model';

export interface IAddress {
  id: number;
  type?: AddressType | null;
  line1?: string | null;
  line2?: string | null;
  country?: string | null;
  state?: string | null;
  pincode?: string | null;
  hasPrefered?: boolean | null;
  landMark?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  employeeId?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
  state?: Pick<IState, 'id'> | null;
  district?: Pick<IDistrict, 'id'> | null;
  taluka?: Pick<ITaluka, 'id'> | null;
  city?: Pick<ICity, 'id'> | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
