import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ModePaiement } from 'app/entities/enumerations/mode-paiement.model';
import { IPaiement, Paiement } from '../paiement.model';

import { PaiementService } from './paiement.service';

describe('Paiement Service', () => {
  let service: PaiementService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaiement;
  let expectedResult: IPaiement | IPaiement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaiementService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      type: ModePaiement.CHEQUE,
      avance: 0,
      etat: false,
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

    it('should create a Paiement', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Paiement()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paiement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          avance: 1,
          etat: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paiement', () => {
      const patchObject = Object.assign(
        {
          avance: 1,
        },
        new Paiement()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paiement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          avance: 1,
          etat: true,
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

    it('should delete a Paiement', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaiementToCollectionIfMissing', () => {
      it('should add a Paiement to an empty array', () => {
        const paiement: IPaiement = { id: 123 };
        expectedResult = service.addPaiementToCollectionIfMissing([], paiement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiement);
      });

      it('should not add a Paiement to an array that contains it', () => {
        const paiement: IPaiement = { id: 123 };
        const paiementCollection: IPaiement[] = [
          {
            ...paiement,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, paiement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paiement to an array that doesn't contain it", () => {
        const paiement: IPaiement = { id: 123 };
        const paiementCollection: IPaiement[] = [{ id: 456 }];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, paiement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiement);
      });

      it('should add only unique Paiement to an array', () => {
        const paiementArray: IPaiement[] = [{ id: 123 }, { id: 456 }, { id: 52850 }];
        const paiementCollection: IPaiement[] = [{ id: 123 }];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, ...paiementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paiement: IPaiement = { id: 123 };
        const paiement2: IPaiement = { id: 456 };
        expectedResult = service.addPaiementToCollectionIfMissing([], paiement, paiement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paiement);
        expect(expectedResult).toContain(paiement2);
      });

      it('should accept null and undefined values', () => {
        const paiement: IPaiement = { id: 123 };
        expectedResult = service.addPaiementToCollectionIfMissing([], null, paiement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paiement);
      });

      it('should return initial array if no Paiement is added', () => {
        const paiementCollection: IPaiement[] = [{ id: 123 }];
        expectedResult = service.addPaiementToCollectionIfMissing(paiementCollection, undefined, null);
        expect(expectedResult).toEqual(paiementCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
