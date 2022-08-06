import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BonCommandeDetailComponent } from './bon-commande-detail.component';

describe('BonCommande Management Detail Component', () => {
  let comp: BonCommandeDetailComponent;
  let fixture: ComponentFixture<BonCommandeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BonCommandeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bonCommande: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BonCommandeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BonCommandeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bonCommande on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bonCommande).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
