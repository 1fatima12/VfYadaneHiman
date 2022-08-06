import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStock, getStockIdentifier } from '../stock.model';

export type EntityResponseType = HttpResponse<IStock>;
export type EntityArrayResponseType = HttpResponse<IStock[]>;

@Injectable({ providedIn: 'root' })
export class StockService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stocks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stock: IStock): Observable<EntityResponseType> {
    return this.http.post<IStock>(this.resourceUrl, stock, { observe: 'response' });
  }

  update(stock: IStock): Observable<EntityResponseType> {
    return this.http.put<IStock>(`${this.resourceUrl}/${getStockIdentifier(stock) as number}`, stock, { observe: 'response' });
  }

  partialUpdate(stock: IStock): Observable<EntityResponseType> {
    return this.http.patch<IStock>(`${this.resourceUrl}/${getStockIdentifier(stock) as number}`, stock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStockToCollectionIfMissing(stockCollection: IStock[], ...stocksToCheck: (IStock | null | undefined)[]): IStock[] {
    const stocks: IStock[] = stocksToCheck.filter(isPresent);
    if (stocks.length > 0) {
      const stockCollectionIdentifiers = stockCollection.map(stockItem => getStockIdentifier(stockItem)!);
      const stocksToAdd = stocks.filter(stockItem => {
        const stockIdentifier = getStockIdentifier(stockItem);
        if (stockIdentifier == null || stockCollectionIdentifiers.includes(stockIdentifier)) {
          return false;
        }
        stockCollectionIdentifiers.push(stockIdentifier);
        return true;
      });
      return [...stocksToAdd, ...stockCollection];
    }
    return stockCollection;
  }
}
