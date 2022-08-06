import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IArrivage, Arrivage } from '../arrivage.model';

import { ArrivageService } from './arrivage.service';

describe('Arrivage Service', () => {
  let service: ArrivageService;
  let httpMock: HttpTestingController;
  let elemDefault: IArrivage;
  let expectedResult: IArrivage | IArrivage[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArrivageService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateArrivage: currentDate,
      prixAchat: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateArrivage: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Arrivage', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateArrivage: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateArrivage: currentDate,
        },
        returnedFromService
      );

      service.create(new Arrivage()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Arrivage', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateArrivage: currentDate.format(DATE_FORMAT),
          prixAchat: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateArrivage: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Arrivage', () => {
      const patchObject = Object.assign(
        {
          dateArrivage: currentDate.format(DATE_FORMAT),
          prixAchat: 1,
        },
        new Arrivage()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateArrivage: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Arrivage', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateArrivage: currentDate.format(DATE_FORMAT),
          prixAchat: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateArrivage: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Arrivage', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addArrivageToCollectionIfMissing', () => {
      it('should add a Arrivage to an empty array', () => {
        const arrivage: IArrivage = { id: 123 };
        expectedResult = service.addArrivageToCollectionIfMissing([], arrivage);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arrivage);
      });

      it('should not add a Arrivage to an array that contains it', () => {
        const arrivage: IArrivage = { id: 123 };
        const arrivageCollection: IArrivage[] = [
          {
            ...arrivage,
          },
          { id: 456 },
        ];
        expectedResult = service.addArrivageToCollectionIfMissing(arrivageCollection, arrivage);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Arrivage to an array that doesn't contain it", () => {
        const arrivage: IArrivage = { id: 123 };
        const arrivageCollection: IArrivage[] = [{ id: 456 }];
        expectedResult = service.addArrivageToCollectionIfMissing(arrivageCollection, arrivage);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arrivage);
      });

      it('should add only unique Arrivage to an array', () => {
        const arrivageArray: IArrivage[] = [{ id: 123 }, { id: 456 }, { id: 22497 }];
        const arrivageCollection: IArrivage[] = [{ id: 123 }];
        expectedResult = service.addArrivageToCollectionIfMissing(arrivageCollection, ...arrivageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const arrivage: IArrivage = { id: 123 };
        const arrivage2: IArrivage = { id: 456 };
        expectedResult = service.addArrivageToCollectionIfMissing([], arrivage, arrivage2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arrivage);
        expect(expectedResult).toContain(arrivage2);
      });

      it('should accept null and undefined values', () => {
        const arrivage: IArrivage = { id: 123 };
        expectedResult = service.addArrivageToCollectionIfMissing([], null, arrivage, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arrivage);
      });

      it('should return initial array if no Arrivage is added', () => {
        const arrivageCollection: IArrivage[] = [{ id: 123 }];
        expectedResult = service.addArrivageToCollectionIfMissing(arrivageCollection, undefined, null);
        expect(expectedResult).toEqual(arrivageCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
