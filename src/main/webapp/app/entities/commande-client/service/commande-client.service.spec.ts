import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICommandeClient, CommandeClient } from '../commande-client.model';

import { CommandeClientService } from './commande-client.service';

describe('CommandeClient Service', () => {
  let service: CommandeClientService;
  let httpMock: HttpTestingController;
  let elemDefault: ICommandeClient;
  let expectedResult: ICommandeClient | ICommandeClient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommandeClientService);
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

    it('should create a CommandeClient', () => {
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

      service.create(new CommandeClient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommandeClient', () => {
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

    it('should partial update a CommandeClient', () => {
      const patchObject = Object.assign(
        {
          dateCom: currentDate.format(DATE_FORMAT),
        },
        new CommandeClient()
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

    it('should return a list of CommandeClient', () => {
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

    it('should delete a CommandeClient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCommandeClientToCollectionIfMissing', () => {
      it('should add a CommandeClient to an empty array', () => {
        const commandeClient: ICommandeClient = { id: 123 };
        expectedResult = service.addCommandeClientToCollectionIfMissing([], commandeClient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandeClient);
      });

      it('should not add a CommandeClient to an array that contains it', () => {
        const commandeClient: ICommandeClient = { id: 123 };
        const commandeClientCollection: ICommandeClient[] = [
          {
            ...commandeClient,
          },
          { id: 456 },
        ];
        expectedResult = service.addCommandeClientToCollectionIfMissing(commandeClientCollection, commandeClient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommandeClient to an array that doesn't contain it", () => {
        const commandeClient: ICommandeClient = { id: 123 };
        const commandeClientCollection: ICommandeClient[] = [{ id: 456 }];
        expectedResult = service.addCommandeClientToCollectionIfMissing(commandeClientCollection, commandeClient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandeClient);
      });

      it('should add only unique CommandeClient to an array', () => {
        const commandeClientArray: ICommandeClient[] = [{ id: 123 }, { id: 456 }, { id: 59863 }];
        const commandeClientCollection: ICommandeClient[] = [{ id: 123 }];
        expectedResult = service.addCommandeClientToCollectionIfMissing(commandeClientCollection, ...commandeClientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commandeClient: ICommandeClient = { id: 123 };
        const commandeClient2: ICommandeClient = { id: 456 };
        expectedResult = service.addCommandeClientToCollectionIfMissing([], commandeClient, commandeClient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commandeClient);
        expect(expectedResult).toContain(commandeClient2);
      });

      it('should accept null and undefined values', () => {
        const commandeClient: ICommandeClient = { id: 123 };
        expectedResult = service.addCommandeClientToCollectionIfMissing([], null, commandeClient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commandeClient);
      });

      it('should return initial array if no CommandeClient is added', () => {
        const commandeClientCollection: ICommandeClient[] = [{ id: 123 }];
        expectedResult = service.addCommandeClientToCollectionIfMissing(commandeClientCollection, undefined, null);
        expect(expectedResult).toEqual(commandeClientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
