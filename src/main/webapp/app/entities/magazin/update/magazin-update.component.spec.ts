import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MagazinService } from '../service/magazin.service';
import { IMagazin, Magazin } from '../magazin.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

import { MagazinUpdateComponent } from './magazin-update.component';

describe('Magazin Management Update Component', () => {
  let comp: MagazinUpdateComponent;
  let fixture: ComponentFixture<MagazinUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let magazinService: MagazinService;
  let locationService: LocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MagazinUpdateComponent],
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
      .overrideTemplate(MagazinUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MagazinUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    magazinService = TestBed.inject(MagazinService);
    locationService = TestBed.inject(LocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call location query and add missing value', () => {
      const magazin: IMagazin = { id: 456 };
      const location: ILocation = { id: 17188 };
      magazin.location = location;

      const locationCollection: ILocation[] = [{ id: 83027 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const expectedCollection: ILocation[] = [location, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ magazin });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, location);
      expect(comp.locationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const magazin: IMagazin = { id: 456 };
      const location: ILocation = { id: 64071 };
      magazin.location = location;

      activatedRoute.data = of({ magazin });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(magazin));
      expect(comp.locationsCollection).toContain(location);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Magazin>>();
      const magazin = { id: 123 };
      jest.spyOn(magazinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magazin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magazin }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(magazinService.update).toHaveBeenCalledWith(magazin);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Magazin>>();
      const magazin = new Magazin();
      jest.spyOn(magazinService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magazin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: magazin }));
      saveSubject.complete();

      // THEN
      expect(magazinService.create).toHaveBeenCalledWith(magazin);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Magazin>>();
      const magazin = { id: 123 };
      jest.spyOn(magazinService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ magazin });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(magazinService.update).toHaveBeenCalledWith(magazin);
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
  });
});
