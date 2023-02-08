import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEmpSalaryInfo } from '../emp-salary-info.model';
import { EmpSalaryInfoService } from '../service/emp-salary-info.service';

import { EmpSalaryInfoRoutingResolveService } from './emp-salary-info-routing-resolve.service';

describe('EmpSalaryInfo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EmpSalaryInfoRoutingResolveService;
  let service: EmpSalaryInfoService;
  let resultEmpSalaryInfo: IEmpSalaryInfo | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(EmpSalaryInfoRoutingResolveService);
    service = TestBed.inject(EmpSalaryInfoService);
    resultEmpSalaryInfo = undefined;
  });

  describe('resolve', () => {
    it('should return IEmpSalaryInfo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmpSalaryInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmpSalaryInfo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmpSalaryInfo = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmpSalaryInfo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEmpSalaryInfo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmpSalaryInfo = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmpSalaryInfo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
