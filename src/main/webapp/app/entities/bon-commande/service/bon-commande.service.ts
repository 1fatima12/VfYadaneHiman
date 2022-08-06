import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBonCommande, getBonCommandeIdentifier } from '../bon-commande.model';

export type EntityResponseType = HttpResponse<IBonCommande>;
export type EntityArrayResponseType = HttpResponse<IBonCommande[]>;

@Injectable({ providedIn: 'root' })
export class BonCommandeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bon-commandes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bonCommande: IBonCommande): Observable<EntityResponseType> {
    return this.http.post<IBonCommande>(this.resourceUrl, bonCommande, { observe: 'response' });
  }

  update(bonCommande: IBonCommande): Observable<EntityResponseType> {
    return this.http.put<IBonCommande>(`${this.resourceUrl}/${getBonCommandeIdentifier(bonCommande) as number}`, bonCommande, {
      observe: 'response',
    });
  }

  partialUpdate(bonCommande: IBonCommande): Observable<EntityResponseType> {
    return this.http.patch<IBonCommande>(`${this.resourceUrl}/${getBonCommandeIdentifier(bonCommande) as number}`, bonCommande, {
      observe: 'response',
    });
  }
public addBonCommande(bonCommande: IBonCommande): Observable<IBonCommande>{
    return this.http.post<IBonCommande>(`${this.resourceUrl}/save`,bonCommande)
  }
  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBonCommande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBonCommande[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBonCommandeToCollectionIfMissing(
    bonCommandeCollection: IBonCommande[],
    ...bonCommandesToCheck: (IBonCommande | null | undefined)[]
  ): IBonCommande[] {
    const bonCommandes: IBonCommande[] = bonCommandesToCheck.filter(isPresent);
    if (bonCommandes.length > 0) {
      const bonCommandeCollectionIdentifiers = bonCommandeCollection.map(bonCommandeItem => getBonCommandeIdentifier(bonCommandeItem)!);
      const bonCommandesToAdd = bonCommandes.filter(bonCommandeItem => {
        const bonCommandeIdentifier = getBonCommandeIdentifier(bonCommandeItem);
        if (bonCommandeIdentifier == null || bonCommandeCollectionIdentifiers.includes(bonCommandeIdentifier)) {
          return false;
        }
        bonCommandeCollectionIdentifiers.push(bonCommandeIdentifier);
        return true;
      });
      return [...bonCommandesToAdd, ...bonCommandeCollection];
    }
    return bonCommandeCollection;
  }
}
