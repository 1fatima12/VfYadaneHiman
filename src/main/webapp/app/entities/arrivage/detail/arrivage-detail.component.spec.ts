import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArrivageDetailComponent } from './arrivage-detail.component';

describe('Arrivage Management Detail Component', () => {
  let comp: ArrivageDetailComponent;
  let fixture: ComponentFixture<ArrivageDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArrivageDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ arrivage: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ArrivageDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ArrivageDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load arrivage on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.arrivage).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
