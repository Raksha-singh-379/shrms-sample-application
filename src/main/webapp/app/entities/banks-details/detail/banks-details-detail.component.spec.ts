import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BanksDetailsDetailComponent } from './banks-details-detail.component';

describe('BanksDetails Management Detail Component', () => {
  let comp: BanksDetailsDetailComponent;
  let fixture: ComponentFixture<BanksDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BanksDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ banksDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BanksDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BanksDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load banksDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.banksDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
