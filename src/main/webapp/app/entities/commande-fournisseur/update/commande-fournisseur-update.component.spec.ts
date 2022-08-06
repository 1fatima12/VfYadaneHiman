import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommandeFournisseurService } from '../service/commande-fournisseur.service';
import { ICommandeFournisseur, CommandeFournisseur } from '../commande-fournisseur.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';

import { CommandeFournisseurUpdateComponent } from './commande-fournisseur-update.component';

describe('CommandeFournisseur Management Update Component', () => {
  let comp: CommandeFournisseurUpdateComponent;
  let fixture: ComponentFixture<CommandeFournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commandeFournisseurService: CommandeFournisseurService;
  let fournisseurService: FournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommandeFournisseurUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CommandeFournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandeFournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commandeFournisseurService = TestBed.inject(CommandeFournisseurService);
    fournisseurService = TestBed.inject(FournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Fournisseur query and add missing value', () => {
      const commandeFournisseur: ICommandeFournisseur = { id: 456 };
      const fournisseur: IFournisseur = { id: 88518 };
      commandeFournisseur.fournisseur = fournisseur;

      const fournisseurCollection: IFournisseur[] = [{ id: 453 }];
      jest.spyOn(fournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: fournisseurCollection })));
      const additionalFournisseurs = [fournisseur];
      const expectedCollection: IFournisseur[] = [...additionalFournisseurs, ...fournisseurCollection];
      jest.spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commandeFournisseur });
      comp.ngOnInit();

      expect(fournisseurService.query).toHaveBeenCalled();
      expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(fournisseurCollection, ...additionalFournisseurs);
      expect(comp.fournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commandeFournisseur: ICommandeFournisseur = { id: 456 };
      const fournisseur: IFournisseur = { id: 71979 };
      commandeFournisseur.fournisseur = fournisseur;

      activatedRoute.data = of({ commandeFournisseur });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(commandeFournisseur));
      expect(comp.fournisseursSharedCollection).toContain(fournisseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeFournisseur>>();
      const commandeFournisseur = { id: 123 };
      jest.spyOn(commandeFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandeFournisseur }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(commandeFournisseurService.update).toHaveBeenCalledWith(commandeFournisseur);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeFournisseur>>();
      const commandeFournisseur = new CommandeFournisseur();
      jest.spyOn(commandeFournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandeFournisseur }));
      saveSubject.complete();

      // THEN
      expect(commandeFournisseurService.create).toHaveBeenCalledWith(commandeFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeFournisseur>>();
      const commandeFournisseur = { id: 123 };
      jest.spyOn(commandeFournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeFournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commandeFournisseurService.update).toHaveBeenCalledWith(commandeFournisseur);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFournisseurById', () => {
      it('Should return tracked Fournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
