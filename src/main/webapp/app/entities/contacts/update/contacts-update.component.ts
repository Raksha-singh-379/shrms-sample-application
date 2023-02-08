import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ContactsFormService, ContactsFormGroup } from './contacts-form.service';
import { IContacts } from '../contacts.model';
import { ContactsService } from '../service/contacts.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ContactType } from 'app/entities/enumerations/contact-type.model';
import { Relationship } from 'app/entities/enumerations/relationship.model';

@Component({
  selector: 'jhi-contacts-update',
  templateUrl: './contacts-update.component.html',
})
export class ContactsUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContacts | null = null;
  contactTypeValues = Object.keys(ContactType);
  relationshipValues = Object.keys(Relationship);

  employeesSharedCollection: IEmployee[] = [];

  editForm: ContactsFormGroup = this.contactsFormService.createContactsFormGroup();

  constructor(
    protected contactsService: ContactsService,
    protected contactsFormService: ContactsFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contacts }) => {
      this.contacts = contacts;
      if (contacts) {
        this.updateForm(contacts);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contacts = this.contactsFormService.getContacts(this.editForm);
    if (contacts.id !== null) {
      this.subscribeToSaveResponse(this.contactsService.update(contacts));
    } else {
      this.subscribeToSaveResponse(this.contactsService.create(contacts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContacts>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contacts: IContacts): void {
    this.contacts = contacts;
    this.contactsFormService.resetForm(this.editForm, contacts);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      contacts.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.contacts?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
