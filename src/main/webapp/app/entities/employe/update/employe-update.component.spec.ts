import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeService } from '../service/employe.service';
import { IEmploye, Employe } from '../employe.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

import { EmployeUpdateComponent } from './employe-update.component';

describe('Employe Management Update Component', () => {
  let comp: EmployeUpdateComponent;
  let fixture: ComponentFixture<EmployeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeService: EmployeService;
  let locationService: LocationService;
  let magazinService: MagazinService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeUpdateComponent],
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
      .overrideTemplate(EmployeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeService = TestBed.inject(EmployeService);
    locationService = TestBed.inject(LocationService);
    magazinService = TestBed.inject(MagazinService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call location query and add missing value', () => {
      const employe: IEmploye = { id: 456 };
      const location: ILocation = { id: 8293 };
      employe.location = location;

      const locationCollection: ILocation[] = [{ id: 5042 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const expectedCollection: ILocation[] = [location, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, location);
      expect(comp.locationsCollection).toEqual(expectedCollection);
    });

    it('Should call Magazin query and add missing value', () => {
      const employe: IEmploye = { id: 456 };
      const magzin: IMagazin = { id: 33017 };
      employe.magzin = magzin;

      const magazinCollection: IMagazin[] = [{ id: 12610 }];
      jest.spyOn(magazinService, 'query').mockReturnValue(of(new HttpResponse({ body: magazinCollection })));
      const additionalMagazins = [magzin];
      const expectedCollection: IMagazin[] = [...additionalMagazins, ...magazinCollection];
      jest.spyOn(magazinService, 'addMagazinToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(magazinService.query).toHaveBeenCalled();
      expect(magazinService.addMagazinToCollectionIfMissing).toHaveBeenCalledWith(magazinCollection, ...additionalMagazins);
      expect(comp.magazinsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employe: IEmploye = { id: 456 };
      const location: ILocation = { id: 18711 };
      employe.location = location;
      const magzin: IMagazin = { id: 34566 };
      employe.magzin = magzin;

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(employe));
      expect(comp.locationsCollection).toContain(location);
      expect(comp.magazinsSharedCollection).toContain(magzin);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = { id: 123 };
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeService.update).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = new Employe();
      jest.spyOn(employeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(employeService.create).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Employe>>();
      const employe = { id: 123 };
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeService.update).toHaveBeenCalledWith(employe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLocationById', () => {
      it('Should return tracked Location primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocationById(0, entity);
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
