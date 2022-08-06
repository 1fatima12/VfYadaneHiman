import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommandeClientDetailComponent } from './commande-client-detail.component';

describe('CommandeClient Management Detail Component', () => {
  let comp: CommandeClientDetailComponent;
  let fixture: ComponentFixture<CommandeClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommandeClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ commandeClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommandeClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommandeClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load commandeClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.commandeClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
