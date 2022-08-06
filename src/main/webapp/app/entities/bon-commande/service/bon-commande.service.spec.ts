import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBonCommande, BonCommande } from '../bon-commande.model';

import { BonCommandeService } from './bon-commande.service';

describe('BonCommande Service', () => {
  let service: BonCommandeService;
  let httpMock: HttpTestingController;
  let elemDefault: IBonCommande;
  let expectedResult: IBonCommande | IBonCommande[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BonCommandeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      qteCommandee: 0,
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

    it('should create a BonCommande', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BonCommande()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BonCommande', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qteCommandee: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BonCommande', () => {
      const patchObject = Object.assign(
        {
          qteCommandee: 1,
        },
        new BonCommande()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BonCommande', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qteCommandee: 1,
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

    it('should delete a BonCommande', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBonCommandeToCollectionIfMissing', () => {
      it('should add a BonCommande to an empty array', () => {
        const bonCommande: IBonCommande = { id: 123 };
        expectedResult = service.addBonCommandeToCollectionIfMissing([], bonCommande);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bonCommande);
      });

      it('should not add a BonCommande to an array that contains it', () => {
        const bonCommande: IBonCommande = { id: 123 };
        const bonCommandeCollection: IBonCommande[] = [
          {
            ...bonCommande,
          },
          { id: 456 },
        ];
        expectedResult = service.addBonCommandeToCollectionIfMissing(bonCommandeCollection, bonCommande);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BonCommande to an array that doesn't contain it", () => {
        const bonCommande: IBonCommande = { id: 123 };
        const bonCommandeCollection: IBonCommande[] = [{ id: 456 }];
        expectedResult = service.addBonCommandeToCollectionIfMissing(bonCommandeCollection, bonCommande);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bonCommande);
      });

      it('should add only unique BonCommande to an array', () => {
        const bonCommandeArray: IBonCommande[] = [{ id: 123 }, { id: 456 }, { id: 54050 }];
        const bonCommandeCollection: IBonCommande[] = [{ id: 123 }];
        expectedResult = service.addBonCommandeToCollectionIfMissing(bonCommandeCollection, ...bonCommandeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bonCommande: IBonCommande = { id: 123 };
        const bonCommande2: IBonCommande = { id: 456 };
        expectedResult = service.addBonCommandeToCollectionIfMissing([], bonCommande, bonCommande2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bonCommande);
        expect(expectedResult).toContain(bonCommande2);
      });

      it('should accept null and undefined values', () => {
        const bonCommande: IBonCommande = { id: 123 };
        expectedResult = service.addBonCommandeToCollectionIfMissing([], null, bonCommande, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bonCommande);
      });

      it('should return initial array if no BonCommande is added', () => {
        const bonCommandeCollection: IBonCommande[] = [{ id: 123 }];
        expectedResult = service.addBonCommandeToCollectionIfMissing(bonCommandeCollection, undefined, null);
        expect(expectedResult).toEqual(bonCommandeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
