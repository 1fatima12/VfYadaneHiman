import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArrivageService } from '../service/arrivage.service';
import { IArrivage, Arrivage } from '../arrivage.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';

import { ArrivageUpdateComponent } from './arrivage-update.component';

describe('Arrivage Management Update Component', () => {
  let comp: ArrivageUpdateComponent;
  let fixture: ComponentFixture<ArrivageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let arrivageService: ArrivageService;
  let produitService: ProduitService;
  let fournisseurService: FournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArrivageUpdateComponent],
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
      .overrideTemplate(ArrivageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArrivageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    arrivageService = TestBed.inject(ArrivageService);
    produitService = TestBed.inject(ProduitService);
    fournisseurService = TestBed.inject(FournisseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const arrivage: IArrivage = { id: 456 };
      const produit: IProduit = { id: 31556 };
      arrivage.produit = produit;

      const produitCollection: IProduit[] = [{ id: 87803 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Fournisseur query and add missing value', () => {
      const arrivage: IArrivage = { id: 456 };
      const fournisseur: IFournisseur = { id: 48232 };
      arrivage.fournisseur = fournisseur;

      const fournisseurCollection: IFournisseur[] = [{ id: 63160 }];
      jest.spyOn(fournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: fournisseurCollection })));
      const additionalFournisseurs = [fournisseur];
      const expectedCollection: IFournisseur[] = [...additionalFournisseurs, ...fournisseurCollection];
      jest.spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      expect(fournisseurService.query).toHaveBeenCalled();
      expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(fournisseurCollection, ...additionalFournisseurs);
      expect(comp.fournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const arrivage: IArrivage = { id: 456 };
      const produit: IProduit = { id: 84222 };
      arrivage.produit = produit;
      const fournisseur: IFournisseur = { id: 54410 };
      arrivage.fournisseur = fournisseur;

      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(arrivage));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.fournisseursSharedCollection).toContain(fournisseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Arrivage>>();
      const arrivage = { id: 123 };
      jest.spyOn(arrivageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arrivage }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(arrivageService.update).toHaveBeenCalledWith(arrivage);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Arrivage>>();
      const arrivage = new Arrivage();
      jest.spyOn(arrivageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: arrivage }));
      saveSubject.complete();

      // THEN
      expect(arrivageService.create).toHaveBeenCalledWith(arrivage);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Arrivage>>();
      const arrivage = { id: 123 };
      jest.spyOn(arrivageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ arrivage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(arrivageService.update).toHaveBeenCalledWith(arrivage);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProduitById', () => {
      it('Should return tracked Produit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProduitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFournisseurById', () => {
      it('Should return tracked Fournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
