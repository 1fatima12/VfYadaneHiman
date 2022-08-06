import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICommandeFournisseur, CommandeFournisseur } from '../commande-fournisseur.model';

import { CommandeFournisseurService } from './commande-fournisseur.service';

describe('CommandeFournisseur Service', () => {
  let service: CommandeFournisseurService;
  let httpMock: HttpTestingController;
  let elemDefault: ICommandeFournisseur;
  let expectedResult: ICommandeFournisseur | ICommandeFournisseur[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommandeFournisseurService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateCom: currentDate,
      designation: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateCom: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CommandeFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCom: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCom: currentDate,
        },
        returnedFromService
      );

      service.create(new CommandeFournisseur()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommandeFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateCom: currentDate.format(DATE_FORMAT),
          designation: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCom: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommandeFournisseur', () => {
      const patchObject = Object.assign(
        {
          dateCom: currentDate.format(DATE_FORMAT),
        },
        new CommandeFournisseur()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCom: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommandeFournisseur', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateCom: currentDate.format(DATE_FORMAT),
          designation: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCom: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CommandeFournisseur', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCommandeFournisseurToCollectionIfMissing', () => {
      it('should add a CommandeFournisseur to an empty array', () => {
        const commandeFournisseur: ICommandeFournisseur = { id: 123 };
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing([], commandeFournisseur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandeFournisseur);
      });

      it('should not add a CommandeFournisseur to an array that contains it', () => {
        const commandeFournisseur: ICommandeFournisseur = { id: 123 };
        const commandeFournisseurCollection: ICommandeFournisseur[] = [
          {
            ...commandeFournisseur,
          },
          { id: 456 },
        ];
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing(commandeFournisseurCollection, commandeFournisseur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommandeFournisseur to an array that doesn't contain it", () => {
        const commandeFournisseur: ICommandeFournisseur = { id: 123 };
        const commandeFournisseurCollection: ICommandeFournisseur[] = [{ id: 456 }];
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing(commandeFournisseurCollection, commandeFournisseur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandeFournisseur);
      });

      it('should add only unique CommandeFournisseur to an array', () => {
        const commandeFournisseurArray: ICommandeFournisseur[] = [{ id: 123 }, { id: 456 }, { id: 52508 }];
        const commandeFournisseurCollection: ICommandeFournisseur[] = [{ id: 123 }];
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing(commandeFournisseurCollection, ...commandeFournisseurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commandeFournisseur: ICommandeFournisseur = { id: 123 };
        const commandeFournisseur2: ICommandeFournisseur = { id: 456 };
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing([], commandeFournisseur, commandeFournisseur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandeFournisseur);
        expect(expectedResult).toContain(commandeFournisseur2);
      });

      it('should accept null and undefined values', () => {
        const commandeFournisseur: ICommandeFournisseur = { id: 123 };
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing([], null, commandeFournisseur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandeFournisseur);
      });

      it('should return initial array if no CommandeFournisseur is added', () => {
        const commandeFournisseurCollection: ICommandeFournisseur[] = [{ id: 123 }];
        expectedResult = service.addCommandeFournisseurToCollectionIfMissing(commandeFournisseurCollection, undefined, null);
        expect(expectedResult).toEqual(commandeFournisseurCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
