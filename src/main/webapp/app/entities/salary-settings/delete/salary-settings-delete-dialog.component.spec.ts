jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SalarySettingsService } from '../service/salary-settings.service';

import { SalarySettingsDeleteDialogComponent } from './salary-settings-delete-dialog.component';

describe('SalarySettings Management Delete Component', () => {
  let comp: SalarySettingsDeleteDialogComponent;
  let fixture: ComponentFixture<SalarySettingsDeleteDialogComponent>;
  let service: SalarySettingsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SalarySettingsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SalarySettingsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalarySettingsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalarySettingsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
