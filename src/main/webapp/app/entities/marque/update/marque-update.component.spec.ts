import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MarqueService } from '../service/marque.service';
import { IMarque, Marque } from '../marque.model';

import { MarqueUpdateComponent } from './marque-update.component';

describe('Marque Management Update Component', () => {
  let comp: MarqueUpdateComponent;
  let fixture: ComponentFixture<MarqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let marqueService: MarqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MarqueUpdateComponent],
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
      .overrideTemplate(MarqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MarqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    marqueService = TestBed.inject(MarqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const marque: IMarque = { id: 456 };

      activatedRoute.data = of({ marque });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(marque));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Marque>>();
      const marque = { id: 123 };
      jest.spyOn(marqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ marque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: marque }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(marqueService.update).toHaveBeenCalledWith(marque);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Marque>>();
      const marque = new Marque();
      jest.spyOn(marqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ marque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: marque }));
      saveSubject.complete();

      // THEN
      expect(marqueService.create).toHaveBeenCalledWith(marque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Marque>>();
      const marque = { id: 123 };
      jest.spyOn(marqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ marque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(marqueService.update).toHaveBeenCalledWith(marque);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
