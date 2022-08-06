import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFacture, getFactureIdentifier } from '../facture.model';

export type EntityResponseType = HttpResponse<IFacture>;
export type EntityArrayResponseType = HttpResponse<IFacture[]>;

@Injectable({ providedIn: 'root' })
export class FactureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/factures');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(facture: IFacture): Observable<EntityResponseType> {
    return this.http.post<IFacture>(this.resourceUrl, facture, { observe: 'response' });
  }

  update(facture: IFacture): Observable<EntityResponseType> {
    return this.http.put<IFacture>(`${this.resourceUrl}/${getFactureIdentifier(facture) as number}`, facture, { observe: 'response' });
  }

  partialUpdate(facture: IFacture): Observable<EntityResponseType> {
    return this.http.patch<IFacture>(`${this.resourceUrl}/${getFactureIdentifier(facture) as number}`, facture, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFactureToCollectionIfMissing(factureCollection: IFacture[], ...facturesToCheck: (IFacture | null | undefined)[]): IFacture[] {
    const factures: IFacture[] = facturesToCheck.filter(isPresent);
    if (factures.length > 0) {
      const factureCollectionIdentifiers = factureCollection.map(factureItem => getFactureIdentifier(factureItem)!);
      const facturesToAdd = factures.filter(factureItem => {
        const factureIdentifier = getFactureIdentifier(factureItem);
        if (factureIdentifier == null || factureCollectionIdentifiers.includes(factureIdentifier)) {
          return false;
        }
        factureCollectionIdentifiers.push(factureIdentifier);
        return true;
      });
      return [...facturesToAdd, ...factureCollection];
    }
    return factureCollection;
  }
}
