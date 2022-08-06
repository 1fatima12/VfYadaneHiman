import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BtiDetailComponent } from './bti-detail.component';

describe('Bti Management Detail Component', () => {
  let comp: BtiDetailComponent;
  let fixture: ComponentFixture<BtiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BtiDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bti: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BtiDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BtiDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bti on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bti).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
