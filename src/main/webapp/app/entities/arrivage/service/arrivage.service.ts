import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArrivage, getArrivageIdentifier } from '../arrivage.model';

export type EntityResponseType = HttpResponse<IArrivage>;
export type EntityArrayResponseType = HttpResponse<IArrivage[]>;

@Injectable({ providedIn: 'root' })
export class ArrivageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/arrivages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(arrivage: IArrivage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivage);
    return this.http
      .post<IArrivage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arrivage: IArrivage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivage);
    return this.http
      .put<IArrivage>(`${this.resourceUrl}/${getArrivageIdentifier(arrivage) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(arrivage: IArrivage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivage);
    return this.http
      .patch<IArrivage>(`${this.resourceUrl}/${getArrivageIdentifier(arrivage) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArrivage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArrivage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addArrivageToCollectionIfMissing(arrivageCollection: IArrivage[], ...arrivagesToCheck: (IArrivage | null | undefined)[]): IArrivage[] {
    const arrivages: IArrivage[] = arrivagesToCheck.filter(isPresent);
    if (arrivages.length > 0) {
      const arrivageCollectionIdentifiers = arrivageCollection.map(arrivageItem => getArrivageIdentifier(arrivageItem)!);
      const arrivagesToAdd = arrivages.filter(arrivageItem => {
        const arrivageIdentifier = getArrivageIdentifier(arrivageItem);
        if (arrivageIdentifier == null || arrivageCollectionIdentifiers.includes(arrivageIdentifier)) {
          return false;
        }
        arrivageCollectionIdentifiers.push(arrivageIdentifier);
        return true;
      });
      return [...arrivagesToAdd, ...arrivageCollection];
    }
    return arrivageCollection;
  }

  protected convertDateFromClient(arrivage: IArrivage): IArrivage {
    return Object.assign({}, arrivage, {
      dateArrivage: arrivage.dateArrivage?.isValid() ? arrivage.dateArrivage.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateArrivage = res.body.dateArrivage ? dayjs(res.body.dateArrivage) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((arrivage: IArrivage) => {
        arrivage.dateArrivage = arrivage.dateArrivage ? dayjs(arrivage.dateArrivage) : undefined;
      });
    }
    return res;
  }
}
