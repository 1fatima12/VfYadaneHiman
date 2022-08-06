import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMagazin, Magazin } from '../magazin.model';

import { MagazinService } from './magazin.service';

describe('Magazin Service', () => {
  let service: MagazinService;
  let httpMock: HttpTestingController;
  let elemDefault: IMagazin;
  let expectedResult: IMagazin | IMagazin[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MagazinService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nomMagazin: 'AAAAAAA',
      adresseMagazin: 'AAAAAAA',
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

    it('should create a Magazin', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Magazin()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Magazin', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomMagazin: 'BBBBBB',
          adresseMagazin: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Magazin', () => {
      const patchObject = Object.assign({}, new Magazin());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Magazin', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nomMagazin: 'BBBBBB',
          adresseMagazin: 'BBBBBB',
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

    it('should delete a Magazin', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMagazinToCollectionIfMissing', () => {
      it('should add a Magazin to an empty array', () => {
        const magazin: IMagazin = { id: 123 };
        expectedResult = service.addMagazinToCollectionIfMissing([], magazin);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magazin);
      });

      it('should not add a Magazin to an array that contains it', () => {
        const magazin: IMagazin = { id: 123 };
        const magazinCollection: IMagazin[] = [
          {
            ...magazin,
          },
          { id: 456 },
        ];
        expectedResult = service.addMagazinToCollectionIfMissing(magazinCollection, magazin);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Magazin to an array that doesn't contain it", () => {
        const magazin: IMagazin = { id: 123 };
        const magazinCollection: IMagazin[] = [{ id: 456 }];
        expectedResult = service.addMagazinToCollectionIfMissing(magazinCollection, magazin);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magazin);
      });

      it('should add only unique Magazin to an array', () => {
        const magazinArray: IMagazin[] = [{ id: 123 }, { id: 456 }, { id: 77223 }];
        const magazinCollection: IMagazin[] = [{ id: 123 }];
        expectedResult = service.addMagazinToCollectionIfMissing(magazinCollection, ...magazinArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const magazin: IMagazin = { id: 123 };
        const magazin2: IMagazin = { id: 456 };
        expectedResult = service.addMagazinToCollectionIfMissing([], magazin, magazin2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(magazin);
        expect(expectedResult).toContain(magazin2);
      });

      it('should accept null and undefined values', () => {
        const magazin: IMagazin = { id: 123 };
        expectedResult = service.addMagazinToCollectionIfMissing([], null, magazin, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(magazin);
      });

      it('should return initial array if no Magazin is added', () => {
        const magazinCollection: IMagazin[] = [{ id: 123 }];
        expectedResult = service.addMagazinToCollectionIfMissing(magazinCollection, undefined, null);
        expect(expectedResult).toEqual(magazinCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
