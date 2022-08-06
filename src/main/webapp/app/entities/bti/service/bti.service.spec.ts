import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IBti, Bti } from '../bti.model';

import { BtiService } from './bti.service';

describe('Bti Service', () => {
  let service: BtiService;
  let httpMock: HttpTestingController;
  let elemDefault: IBti;
  let expectedResult: IBti | IBti[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BtiService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      numOrdre: 0,
      date: currentDate,
      ref: 0,
      qte: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Bti', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new Bti()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bti', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numOrdre: 1,
          date: currentDate.format(DATE_FORMAT),
          ref: 1,
          qte: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bti', () => {
      const patchObject = Object.assign(
        {
          numOrdre: 1,
          date: currentDate.format(DATE_FORMAT),
          ref: 1,
          qte: 1,
        },
        new Bti()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bti', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numOrdre: 1,
          date: currentDate.format(DATE_FORMAT),
          ref: 1,
          qte: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Bti', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBtiToCollectionIfMissing', () => {
      it('should add a Bti to an empty array', () => {
        const bti: IBti = { id: 123 };
        expectedResult = service.addBtiToCollectionIfMissing([], bti);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bti);
      });

      it('should not add a Bti to an array that contains it', () => {
        const bti: IBti = { id: 123 };
        const btiCollection: IBti[] = [
          {
            ...bti,
          },
          { id: 456 },
        ];
        expectedResult = service.addBtiToCollectionIfMissing(btiCollection, bti);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bti to an array that doesn't contain it", () => {
        const bti: IBti = { id: 123 };
        const btiCollection: IBti[] = [{ id: 456 }];
        expectedResult = service.addBtiToCollectionIfMissing(btiCollection, bti);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bti);
      });

      it('should add only unique Bti to an array', () => {
        const btiArray: IBti[] = [{ id: 123 }, { id: 456 }, { id: 18542 }];
        const btiCollection: IBti[] = [{ id: 123 }];
        expectedResult = service.addBtiToCollectionIfMissing(btiCollection, ...btiArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bti: IBti = { id: 123 };
        const bti2: IBti = { id: 456 };
        expectedResult = service.addBtiToCollectionIfMissing([], bti, bti2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bti);
        expect(expectedResult).toContain(bti2);
      });

      it('should accept null and undefined values', () => {
        const bti: IBti = { id: 123 };
        expectedResult = service.addBtiToCollectionIfMissing([], null, bti, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bti);
      });

      it('should return initial array if no Bti is added', () => {
        const btiCollection: IBti[] = [{ id: 123 }];
        expectedResult = service.addBtiToCollectionIfMissing(btiCollection, undefined, null);
        expect(expectedResult).toEqual(btiCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
