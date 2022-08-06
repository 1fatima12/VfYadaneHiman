import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagazinDetailComponent } from './magazin-detail.component';

describe('Magazin Management Detail Component', () => {
  let comp: MagazinDetailComponent;
  let fixture: ComponentFixture<MagazinDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MagazinDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ magazin: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MagazinDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MagazinDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load magazin on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.magazin).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
