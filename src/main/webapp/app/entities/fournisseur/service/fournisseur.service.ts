import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFournisseur, getFournisseurIdentifier } from '../fournisseur.model';

export type EntityResponseType = HttpResponse<IFournisseur>;
export type EntityArrayResponseType = HttpResponse<IFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class FournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fournisseur: IFournisseur): Observable<EntityResponseType> {
    return this.http.post<IFournisseur>(this.resourceUrl, fournisseur, { observe: 'response' });
  }

  update(fournisseur: IFournisseur): Observable<EntityResponseType> {
    return this.http.put<IFournisseur>(`${this.resourceUrl}/${getFournisseurIdentifier(fournisseur) as number}`, fournisseur, {
      observe: 'response',
    });
  }

  partialUpdate(fournisseur: IFournisseur): Observable<EntityResponseType> {
    return this.http.patch<IFournisseur>(`${this.resourceUrl}/${getFournisseurIdentifier(fournisseur) as number}`, fournisseur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFournisseurToCollectionIfMissing(
    fournisseurCollection: IFournisseur[],
    ...fournisseursToCheck: (IFournisseur | null | undefined)[]
  ): IFournisseur[] {
    const fournisseurs: IFournisseur[] = fournisseursToCheck.filter(isPresent);
    if (fournisseurs.length > 0) {
      const fournisseurCollectionIdentifiers = fournisseurCollection.map(fournisseurItem => getFournisseurIdentifier(fournisseurItem)!);
      const fournisseursToAdd = fournisseurs.filter(fournisseurItem => {
        const fournisseurIdentifier = getFournisseurIdentifier(fournisseurItem);
        if (fournisseurIdentifier == null || fournisseurCollectionIdentifiers.includes(fournisseurIdentifier)) {
          return false;
        }
        fournisseurCollectionIdentifiers.push(fournisseurIdentifier);
        return true;
      });
      return [...fournisseursToAdd, ...fournisseurCollection];
    }
    return fournisseurCollection;
  }
}
