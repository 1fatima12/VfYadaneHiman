import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBti, getBtiIdentifier } from '../bti.model';

export type EntityResponseType = HttpResponse<IBti>;
export type EntityArrayResponseType = HttpResponse<IBti[]>;

@Injectable({ providedIn: 'root' })
export class BtiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/btis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bti: IBti): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bti);
    return this.http
      .post<IBti>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bti: IBti): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bti);
    return this.http
      .put<IBti>(`${this.resourceUrl}/${getBtiIdentifier(bti) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bti: IBti): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bti);
    return this.http
      .patch<IBti>(`${this.resourceUrl}/${getBtiIdentifier(bti) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBti>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBti[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBtiToCollectionIfMissing(btiCollection: IBti[], ...btisToCheck: (IBti | null | undefined)[]): IBti[] {
    const btis: IBti[] = btisToCheck.filter(isPresent);
    if (btis.length > 0) {
      const btiCollectionIdentifiers = btiCollection.map(btiItem => getBtiIdentifier(btiItem)!);
      const btisToAdd = btis.filter(btiItem => {
        const btiIdentifier = getBtiIdentifier(btiItem);
        if (btiIdentifier == null || btiCollectionIdentifiers.includes(btiIdentifier)) {
          return false;
        }
        btiCollectionIdentifiers.push(btiIdentifier);
        return true;
      });
      return [...btisToAdd, ...btiCollection];
    }
    return btiCollection;
  }

  protected convertDateFromClient(bti: IBti): IBti {
    return Object.assign({}, bti, {
      date: bti.date?.isValid() ? bti.date.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bti: IBti) => {
        bti.date = bti.date ? dayjs(bti.date) : undefined;
      });
    }
    return res;
  }
}
