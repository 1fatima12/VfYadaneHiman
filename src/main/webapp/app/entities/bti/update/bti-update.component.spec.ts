import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BtiService } from '../service/bti.service';
import { IBti, Bti } from '../bti.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

import { BtiUpdateComponent } from './bti-update.component';

describe('Bti Management Update Component', () => {
  let comp: BtiUpdateComponent;
  let fixture: ComponentFixture<BtiUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let btiService: BtiService;
  let produitService: ProduitService;
  let magazinService: MagazinService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BtiUpdateComponent],
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
      .overrideTemplate(BtiUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BtiUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    btiService = TestBed.inject(BtiService);
    produitService = TestBed.inject(ProduitService);
    magazinService = TestBed.inject(MagazinService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const bti: IBti = { id: 456 };
      const produit: IProduit = { id: 76336 };
      bti.produit = produit;

      const produitCollection: IProduit[] = [{ id: 43012 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Magazin query and add missing value', () => {
      const bti: IBti = { id: 456 };
      const magazin: IMagazin = { id: 62048 };
      bti.magazin = magazin;

      const magazinCollection: IMagazin[] = [{ id: 63878 }];
      jest.spyOn(magazinService, 'query').mockReturnValue(of(new HttpResponse({ body: magazinCollection })));
      const additionalMagazins = [magazin];
      const expectedCollection: IMagazin[] = [...additionalMagazins, ...magazinCollection];
      jest.spyOn(magazinService, 'addMagazinToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      expect(magazinService.query).toHaveBeenCalled();
      expect(magazinService.addMagazinToCollectionIfMissing).toHaveBeenCalledWith(magazinCollection, ...additionalMagazins);
      expect(comp.magazinsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bti: IBti = { id: 456 };
      const produit: IProduit = { id: 38712 };
      bti.produit = produit;
      const magazin: IMagazin = { id: 756 };
      bti.magazin = magazin;

      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bti));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.magazinsSharedCollection).toContain(magazin);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bti>>();
      const bti = { id: 123 };
      jest.spyOn(btiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bti }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(btiService.update).toHaveBeenCalledWith(bti);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bti>>();
      const bti = new Bti();
      jest.spyOn(btiService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bti }));
      saveSubject.complete();

      // THEN
      expect(btiService.create).toHaveBeenCalledWith(bti);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bti>>();
      const bti = { id: 123 };
      jest.spyOn(btiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bti });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(btiService.update).toHaveBeenCalledWith(bti);
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

    describe('trackMagazinById', () => {
      it('Should return tracked Magazin primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMagazinById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
