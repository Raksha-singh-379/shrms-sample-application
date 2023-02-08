import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBranch, NewBranch } from '../branch.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBranch for edit and NewBranchFormGroupInput for create.
 */
type BranchFormGroupInput = IBranch | PartialWithRequiredKeyOf<NewBranch>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBranch | NewBranch> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type BranchFormRawValue = FormValueOf<IBranch>;

type NewBranchFormRawValue = FormValueOf<NewBranch>;

type BranchFormDefaults = Pick<NewBranch, 'id' | 'isHeadOffice' | 'isActivate' | 'createdOn' | 'lastModified'>;

type BranchFormGroupContent = {
  id: FormControl<BranchFormRawValue['id'] | NewBranch['id']>;
  branchName: FormControl<BranchFormRawValue['branchName']>;
  description: FormControl<BranchFormRawValue['description']>;
  branchcode: FormControl<BranchFormRawValue['branchcode']>;
  ifscCode: FormControl<BranchFormRawValue['ifscCode']>;
  micrCode: FormControl<BranchFormRawValue['micrCode']>;
  swiftCode: FormControl<BranchFormRawValue['swiftCode']>;
  ibanCode: FormControl<BranchFormRawValue['ibanCode']>;
  isHeadOffice: FormControl<BranchFormRawValue['isHeadOffice']>;
  routingTransitNo: FormControl<BranchFormRawValue['routingTransitNo']>;
  phone: FormControl<BranchFormRawValue['phone']>;
  email: FormControl<BranchFormRawValue['email']>;
  webSite: FormControl<BranchFormRawValue['webSite']>;
  fax: FormControl<BranchFormRawValue['fax']>;
  isActivate: FormControl<BranchFormRawValue['isActivate']>;
  branchType: FormControl<BranchFormRawValue['branchType']>;
  createdOn: FormControl<BranchFormRawValue['createdOn']>;
  createdBy: FormControl<BranchFormRawValue['createdBy']>;
  deleted: FormControl<BranchFormRawValue['deleted']>;
  lastModified: FormControl<BranchFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<BranchFormRawValue['lastModifiedBy']>;
  address: FormControl<BranchFormRawValue['address']>;
  company: FormControl<BranchFormRawValue['company']>;
  branch: FormControl<BranchFormRawValue['branch']>;
};

export type BranchFormGroup = FormGroup<BranchFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BranchFormService {
  createBranchFormGroup(branch: BranchFormGroupInput = { id: null }): BranchFormGroup {
    const branchRawValue = this.convertBranchToBranchRawValue({
      ...this.getFormDefaults(),
      ...branch,
    });
    return new FormGroup<BranchFormGroupContent>({
      id: new FormControl(
        { value: branchRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      branchName: new FormControl(branchRawValue.branchName, {
        validators: [Validators.required],
      }),
      description: new FormControl(branchRawValue.description),
      branchcode: new FormControl(branchRawValue.branchcode),
      ifscCode: new FormControl(branchRawValue.ifscCode),
      micrCode: new FormControl(branchRawValue.micrCode),
      swiftCode: new FormControl(branchRawValue.swiftCode),
      ibanCode: new FormControl(branchRawValue.ibanCode),
      isHeadOffice: new FormControl(branchRawValue.isHeadOffice),
      routingTransitNo: new FormControl(branchRawValue.routingTransitNo),
      phone: new FormControl(branchRawValue.phone),
      email: new FormControl(branchRawValue.email),
      webSite: new FormControl(branchRawValue.webSite),
      fax: new FormControl(branchRawValue.fax),
      isActivate: new FormControl(branchRawValue.isActivate),
      branchType: new FormControl(branchRawValue.branchType),
      createdOn: new FormControl(branchRawValue.createdOn),
      createdBy: new FormControl(branchRawValue.createdBy),
      deleted: new FormControl(branchRawValue.deleted),
      lastModified: new FormControl(branchRawValue.lastModified),
      lastModifiedBy: new FormControl(branchRawValue.lastModifiedBy),
      address: new FormControl(branchRawValue.address),
      company: new FormControl(branchRawValue.company),
      branch: new FormControl(branchRawValue.branch),
    });
  }

  getBranch(form: BranchFormGroup): IBranch | NewBranch {
    return this.convertBranchRawValueToBranch(form.getRawValue() as BranchFormRawValue | NewBranchFormRawValue);
  }

  resetForm(form: BranchFormGroup, branch: BranchFormGroupInput): void {
    const branchRawValue = this.convertBranchToBranchRawValue({ ...this.getFormDefaults(), ...branch });
    form.reset(
      {
        ...branchRawValue,
        id: { value: branchRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BranchFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isHeadOffice: false,
      isActivate: false,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertBranchRawValueToBranch(rawBranch: BranchFormRawValue | NewBranchFormRawValue): IBranch | NewBranch {
    return {
      ...rawBranch,
      createdOn: dayjs(rawBranch.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawBranch.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertBranchToBranchRawValue(
    branch: IBranch | (Partial<NewBranch> & BranchFormDefaults)
  ): BranchFormRawValue | PartialWithRequiredKeyOf<NewBranchFormRawValue> {
    return {
      ...branch,
      createdOn: branch.createdOn ? branch.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: branch.lastModified ? branch.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
