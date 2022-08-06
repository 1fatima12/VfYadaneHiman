import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaiement, getPaiementIdentifier } from '../paiement.model';

export type EntityResponseType = HttpResponse<IPaiement>;
export type EntityArrayResponseType = HttpResponse<IPaiement[]>;

@Injectable({ providedIn: 'root' })
export class PaiementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paiements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paiement: IPaiement): Observable<EntityResponseType> {
    return this.http.post<IPaiement>(this.resourceUrl, paiement, { observe: 'response' });
  }

  update(paiement: IPaiement): Observable<EntityResponseType> {
    return this.http.put<IPaiement>(`${this.resourceUrl}/${getPaiementIdentifier(paiement) as number}`, paiement, { observe: 'response' });
  }

  partialUpdate(paiement: IPaiement): Observable<EntityResponseType> {
    return this.http.patch<IPaiement>(`${this.resourceUrl}/${getPaiementIdentifier(paiement) as number}`, paiement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaiement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaiement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaiementToCollectionIfMissing(paiementCollection: IPaiement[], ...paiementsToCheck: (IPaiement | null | undefined)[]): IPaiement[] {
    const paiements: IPaiement[] = paiementsToCheck.filter(isPresent);
    if (paiements.length > 0) {
      const paiementCollectionIdentifiers = paiementCollection.map(paiementItem => getPaiementIdentifier(paiementItem)!);
      const paiementsToAdd = paiements.filter(paiementItem => {
        const paiementIdentifier = getPaiementIdentifier(paiementItem);
        if (paiementIdentifier == null || paiementCollectionIdentifiers.includes(paiementIdentifier)) {
          return false;
        }
        paiementCollectionIdentifiers.push(paiementIdentifier);
        return true;
      });
      return [...paiementsToAdd, ...paiementCollection];
    }
    return paiementCollection;
  }
}
