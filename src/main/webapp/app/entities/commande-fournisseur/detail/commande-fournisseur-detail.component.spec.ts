import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommandeFournisseurDetailComponent } from './commande-fournisseur-detail.component';

describe('CommandeFournisseur Management Detail Component', () => {
  let comp: CommandeFournisseurDetailComponent;
  let fixture: ComponentFixture<CommandeFournisseurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommandeFournisseurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ commandeFournisseur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommandeFournisseurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommandeFournisseurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load commandeFournisseur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.commandeFournisseur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
