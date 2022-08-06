import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FactureService } from '../service/facture.service';
import { IFacture, Facture } from '../facture.model';
import { IArrivage } from 'app/entities/arrivage/arrivage.model';
import { ArrivageService } from 'app/entities/arrivage/service/arrivage.service';

import { FactureUpdateComponent } from './facture-update.component';

describe('Facture Management Update Component', () => {
  let comp: FactureUpdateComponent;
  let fixture: ComponentFixture<FactureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factureService: FactureService;
  let arrivageService: ArrivageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FactureUpdateComponent],
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
      .overrideTemplate(FactureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factureService = TestBed.inject(FactureService);
    arrivageService = TestBed.inject(ArrivageService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call arrivage query and add missing value', () => {
      const facture: IFacture = { id: 456 };
      const arrivage: IArrivage = { id: 85248 };
      facture.arrivage = arrivage;

      const arrivageCollection: IArrivage[] = [{ id: 18986 }];
      jest.spyOn(arrivageService, 'query').mockReturnValue(of(new HttpResponse({ body: arrivageCollection })));
      const expectedCollection: IArrivage[] = [arrivage, ...arrivageCollection];
      jest.spyOn(arrivageService, 'addArrivageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ facture });
      comp.ngOnInit();

      expect(arrivageService.query).toHaveBeenCalled();
      expect(arrivageService.addArrivageToCollectionIfMissing).toHaveBeenCalledWith(arrivageCollection, arrivage);
      expect(comp.arrivagesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const facture: IFacture = { id: 456 };
      const arrivage: IArrivage = { id: 25285 };
      facture.arrivage = arrivage;

      activatedRoute.data = of({ facture });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(facture));
      expect(comp.arrivagesCollection).toContain(arrivage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Facture>>();
      const facture = { id: 123 };
      jest.spyOn(factureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: facture }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(factureService.update).toHaveBeenCalledWith(facture);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Facture>>();
      const facture = new Facture();
      jest.spyOn(factureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: facture }));
      saveSubject.complete();

      // THEN
      expect(factureService.create).toHaveBeenCalledWith(facture);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Facture>>();
      const facture = { id: 123 };
      jest.spyOn(factureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facture });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factureService.update).toHaveBeenCalledWith(facture);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackArrivageById', () => {
      it('Should return tracked Arrivage primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackArrivageById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
