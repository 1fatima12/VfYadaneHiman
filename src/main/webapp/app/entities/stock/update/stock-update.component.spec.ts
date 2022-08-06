import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StockService } from '../service/stock.service';
import { IStock, Stock } from '../stock.model';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

import { StockUpdateComponent } from './stock-update.component';

describe('Stock Management Update Component', () => {
  let comp: StockUpdateComponent;
  let fixture: ComponentFixture<StockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stockService: StockService;
  let produitService: ProduitService;
  let magazinService: MagazinService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StockUpdateComponent],
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
      .overrideTemplate(StockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stockService = TestBed.inject(StockService);
    produitService = TestBed.inject(ProduitService);
    magazinService = TestBed.inject(MagazinService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Produit query and add missing value', () => {
      const stock: IStock = { id: 456 };
      const produit: IProduit = { id: 3391 };
      stock.produit = produit;

      const produitCollection: IProduit[] = [{ id: 52524 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Magazin query and add missing value', () => {
      const stock: IStock = { id: 456 };
      const magazin: IMagazin = { id: 73888 };
      stock.magazin = magazin;

      const magazinCollection: IMagazin[] = [{ id: 6008 }];
      jest.spyOn(magazinService, 'query').mockReturnValue(of(new HttpResponse({ body: magazinCollection })));
      const additionalMagazins = [magazin];
      const expectedCollection: IMagazin[] = [...additionalMagazins, ...magazinCollection];
      jest.spyOn(magazinService, 'addMagazinToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(magazinService.query).toHaveBeenCalled();
      expect(magazinService.addMagazinToCollectionIfMissing).toHaveBeenCalledWith(magazinCollection, ...additionalMagazins);
      expect(comp.magazinsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const stock: IStock = { id: 456 };
      const produit: IProduit = { id: 45591 };
      stock.produit = produit;
      const magazin: IMagazin = { id: 35185 };
      stock.magazin = magazin;

      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(stock));
      expect(comp.produitsSharedCollection).toContain(produit);
      expect(comp.magazinsSharedCollection).toContain(magazin);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Stock>>();
      const stock = { id: 123 };
      jest.spyOn(stockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stock }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(stockService.update).toHaveBeenCalledWith(stock);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Stock>>();
      const stock = new Stock();
      jest.spyOn(stockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stock }));
      saveSubject.complete();

      // THEN
      expect(stockService.create).toHaveBeenCalledWith(stock);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Stock>>();
      const stock = { id: 123 };
      jest.spyOn(stockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stockService.update).toHaveBeenCalledWith(stock);
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
