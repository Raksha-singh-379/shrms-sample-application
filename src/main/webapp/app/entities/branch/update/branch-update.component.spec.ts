import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BranchFormService } from './branch-form.service';
import { BranchService } from '../service/branch.service';
import { IBranch } from '../branch.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IRegion } from 'app/entities/region/region.model';
import { RegionService } from 'app/entities/region/service/region.service';

import { BranchUpdateComponent } from './branch-update.component';

describe('Branch Management Update Component', () => {
  let comp: BranchUpdateComponent;
  let fixture: ComponentFixture<BranchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let branchFormService: BranchFormService;
  let branchService: BranchService;
  let addressService: AddressService;
  let companyService: CompanyService;
  let regionService: RegionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BranchUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BranchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BranchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    branchFormService = TestBed.inject(BranchFormService);
    branchService = TestBed.inject(BranchService);
    addressService = TestBed.inject(AddressService);
    companyService = TestBed.inject(CompanyService);
    regionService = TestBed.inject(RegionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Address query and add missing value', () => {
      const branch: IBranch = { id: 456 };
      const address: IAddress = { id: 72533 };
      branch.address = address;

      const addressCollection: IAddress[] = [{ id: 17711 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
      const additionalAddresses = [address];
      const expectedCollection: IAddress[] = [...additionalAddresses, ...addressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(
        addressCollection,
        ...additionalAddresses.map(expect.objectContaining)
      );
      expect(comp.addressesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const branch: IBranch = { id: 456 };
      const company: ICompany = { id: 39318 };
      branch.company = company;

      const companyCollection: ICompany[] = [{ id: 45353 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Region query and add missing value', () => {
      const branch: IBranch = { id: 456 };
      const branch: IRegion = { id: 65109 };
      branch.branch = branch;

      const regionCollection: IRegion[] = [{ id: 67930 }];
      jest.spyOn(regionService, 'query').mockReturnValue(of(new HttpResponse({ body: regionCollection })));
      const additionalRegions = [branch];
      const expectedCollection: IRegion[] = [...additionalRegions, ...regionCollection];
      jest.spyOn(regionService, 'addRegionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      expect(regionService.query).toHaveBeenCalled();
      expect(regionService.addRegionToCollectionIfMissing).toHaveBeenCalledWith(
        regionCollection,
        ...additionalRegions.map(expect.objectContaining)
      );
      expect(comp.regionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const branch: IBranch = { id: 456 };
      const address: IAddress = { id: 35745 };
      branch.address = address;
      const company: ICompany = { id: 87444 };
      branch.company = company;
      const branch: IRegion = { id: 3934 };
      branch.branch = branch;

      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      expect(comp.addressesSharedCollection).toContain(address);
      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.regionsSharedCollection).toContain(branch);
      expect(comp.branch).toEqual(branch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBranch>>();
      const branch = { id: 123 };
      jest.spyOn(branchFormService, 'getBranch').mockReturnValue(branch);
      jest.spyOn(branchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: branch }));
      saveSubject.complete();

      // THEN
      expect(branchFormService.getBranch).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(branchService.update).toHaveBeenCalledWith(expect.objectContaining(branch));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBranch>>();
      const branch = { id: 123 };
      jest.spyOn(branchFormService, 'getBranch').mockReturnValue({ id: null });
      jest.spyOn(branchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ branch: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: branch }));
      saveSubject.complete();

      // THEN
      expect(branchFormService.getBranch).toHaveBeenCalled();
      expect(branchService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBranch>>();
      const branch = { id: 123 };
      jest.spyOn(branchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ branch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(branchService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAddress', () => {
      it('Should forward to addressService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(addressService, 'compareAddress');
        comp.compareAddress(entity, entity2);
        expect(addressService.compareAddress).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRegion', () => {
      it('Should forward to regionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(regionService, 'compareRegion');
        comp.compareRegion(entity, entity2);
        expect(regionService.compareRegion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
