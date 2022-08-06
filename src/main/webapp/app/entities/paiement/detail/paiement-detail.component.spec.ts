import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaiementDetailComponent } from './paiement-detail.component';

describe('Paiement Management Detail Component', () => {
  let comp: PaiementDetailComponent;
  let fixture: ComponentFixture<PaiementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaiementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paiement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaiementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaiementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paiement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paiement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
