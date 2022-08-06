import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMarque, Marque } from '../marque.model';

import { MarqueService } from './marque.service';

describe('Marque Service', () => {
  let service: MarqueService;
  let httpMock: HttpTestingController;
  let elemDefault: IMarque;
  let expectedResult: IMarque | IMarque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MarqueService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nomMarque: 'AAAAAAA',
      logoContentType: 'image/png',
      logo: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Marque', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Marque()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Marque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomMarque: 'BBBBBB',
          logo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Marque', () => {
      const patchObject = Object.assign(
        {
          nomMarque: 'BBBBBB',
          logo: 'BBBBBB',
        },
        new Marque()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Marque', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomMarque: 'BBBBBB',
          logo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Marque', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMarqueToCollectionIfMissing', () => {
      it('should add a Marque to an empty array', () => {
        const marque: IMarque = { id: 123 };
        expectedResult = service.addMarqueToCollectionIfMissing([], marque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(marque);
      });

      it('should not add a Marque to an array that contains it', () => {
        const marque: IMarque = { id: 123 };
        const marqueCollection: IMarque[] = [
          {
            ...marque,
          },
          { id: 456 },
        ];
        expectedResult = service.addMarqueToCollectionIfMissing(marqueCollection, marque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Marque to an array that doesn't contain it", () => {
        const marque: IMarque = { id: 123 };
        const marqueCollection: IMarque[] = [{ id: 456 }];
        expectedResult = service.addMarqueToCollectionIfMissing(marqueCollection, marque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(marque);
      });

      it('should add only unique Marque to an array', () => {
        const marqueArray: IMarque[] = [{ id: 123 }, { id: 456 }, { id: 54458 }];
        const marqueCollection: IMarque[] = [{ id: 123 }];
        expectedResult = service.addMarqueToCollectionIfMissing(marqueCollection, ...marqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const marque: IMarque = { id: 123 };
        const marque2: IMarque = { id: 456 };
        expectedResult = service.addMarqueToCollectionIfMissing([], marque, marque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(marque);
        expect(expectedResult).toContain(marque2);
      });

      it('should accept null and undefined values', () => {
        const marque: IMarque = { id: 123 };
        expectedResult = service.addMarqueToCollectionIfMissing([], null, marque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(marque);
      });

      it('should return initial array if no Marque is added', () => {
        const marqueCollection: IMarque[] = [{ id: 123 }];
        expectedResult = service.addMarqueToCollectionIfMissing(marqueCollection, undefined, null);
        expect(expectedResult).toEqual(marqueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
