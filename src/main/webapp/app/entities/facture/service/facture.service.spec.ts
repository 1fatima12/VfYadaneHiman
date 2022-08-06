import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFacture, Facture } from '../facture.model';

import { FactureService } from './facture.service';

describe('Facture Service', () => {
  let service: FactureService;
  let httpMock: HttpTestingController;
  let elemDefault: IFacture;
  let expectedResult: IFacture | IFacture[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FactureService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      idFacture: 0,
      montant: 0,
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

    it('should create a Facture', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Facture()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Facture', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idFacture: 1,
          montant: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Facture', () => {
      const patchObject = Object.assign(
        {
          montant: 1,
        },
        new Facture()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Facture', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idFacture: 1,
          montant: 1,
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

    it('should delete a Facture', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFactureToCollectionIfMissing', () => {
      it('should add a Facture to an empty array', () => {
        const facture: IFacture = { id: 123 };
        expectedResult = service.addFactureToCollectionIfMissing([], facture);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(facture);
      });

      it('should not add a Facture to an array that contains it', () => {
        const facture: IFacture = { id: 123 };
        const factureCollection: IFacture[] = [
          {
            ...facture,
          },
          { id: 456 },
        ];
        expectedResult = service.addFactureToCollectionIfMissing(factureCollection, facture);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Facture to an array that doesn't contain it", () => {
        const facture: IFacture = { id: 123 };
        const factureCollection: IFacture[] = [{ id: 456 }];
        expectedResult = service.addFactureToCollectionIfMissing(factureCollection, facture);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(facture);
      });

      it('should add only unique Facture to an array', () => {
        const factureArray: IFacture[] = [{ id: 123 }, { id: 456 }, { id: 92471 }];
        const factureCollection: IFacture[] = [{ id: 123 }];
        expectedResult = service.addFactureToCollectionIfMissing(factureCollection, ...factureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const facture: IFacture = { id: 123 };
        const facture2: IFacture = { id: 456 };
        expectedResult = service.addFactureToCollectionIfMissing([], facture, facture2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(facture);
        expect(expectedResult).toContain(facture2);
      });

      it('should accept null and undefined values', () => {
        const facture: IFacture = { id: 123 };
        expectedResult = service.addFactureToCollectionIfMissing([], null, facture, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(facture);
      });

      it('should return initial array if no Facture is added', () => {
        const factureCollection: IFacture[] = [{ id: 123 }];
        expectedResult = service.addFactureToCollectionIfMissing(factureCollection, undefined, null);
        expect(expectedResult).toEqual(factureCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
