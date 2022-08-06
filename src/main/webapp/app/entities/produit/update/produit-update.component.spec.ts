import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProduitService } from '../service/produit.service';
import { IProduit, Produit } from '../produit.model';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IMarque } from 'app/entities/marque/marque.model';
import { MarqueService } from 'app/entities/marque/service/marque.service';

import { ProduitUpdateComponent } from './produit-update.component';

describe('Produit Management Update Component', () => {
  let comp: ProduitUpdateComponent;
  let fixture: ComponentFixture<ProduitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let produitService: ProduitService;
  let categorieService: CategorieService;
  let marqueService: MarqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProduitUpdateComponent],
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
      .overrideTemplate(ProduitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProduitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    produitService = TestBed.inject(ProduitService);
    categorieService = TestBed.inject(CategorieService);
    marqueService = TestBed.inject(MarqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Categorie query and add missing value', () => {
      const produit: IProduit = { id: 456 };
      const categorie: ICategorie = { id: 90245 };
      produit.categorie = categorie;

      const categorieCollection: ICategorie[] = [{ id: 28130 }];
      jest.spyOn(categorieService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCollection })));
      const additionalCategories = [categorie];
      const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
      jest.spyOn(categorieService, 'addCategorieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(categorieService.query).toHaveBeenCalled();
      expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(categorieCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Marque query and add missing value', () => {
      const produit: IProduit = { id: 456 };
      const marque: IMarque = { id: 28179 };
      produit.marque = marque;

      const marqueCollection: IMarque[] = [{ id: 93245 }];
      jest.spyOn(marqueService, 'query').mockReturnValue(of(new HttpResponse({ body: marqueCollection })));
      const additionalMarques = [marque];
      const expectedCollection: IMarque[] = [...additionalMarques, ...marqueCollection];
      jest.spyOn(marqueService, 'addMarqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(marqueService.query).toHaveBeenCalled();
      expect(marqueService.addMarqueToCollectionIfMissing).toHaveBeenCalledWith(marqueCollection, ...additionalMarques);
      expect(comp.marquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const produit: IProduit = { id: 456 };
      const categorie: ICategorie = { id: 75107 };
      produit.categorie = categorie;
      const marque: IMarque = { id: 78221 };
      produit.marque = marque;

      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(produit));
      expect(comp.categoriesSharedCollection).toContain(categorie);
      expect(comp.marquesSharedCollection).toContain(marque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = { id: 123 };
      jest.spyOn(produitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produit }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(produitService.update).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = new Produit();
      jest.spyOn(produitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produit }));
      saveSubject.complete();

      // THEN
      expect(produitService.create).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Produit>>();
      const produit = { id: 123 };
      jest.spyOn(produitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(produitService.update).toHaveBeenCalledWith(produit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCategorieById', () => {
      it('Should return tracked Categorie primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategorieById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMarqueById', () => {
      it('Should return tracked Marque primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMarqueById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
