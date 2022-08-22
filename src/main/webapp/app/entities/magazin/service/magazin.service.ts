import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMagazin, getMagazinIdentifier } from '../magazin.model';

export type EntityResponseType = HttpResponse<IMagazin>;
export type EntityArrayResponseType = HttpResponse<IMagazin[]>;

@Injectable({ providedIn: 'root' })
export class MagazinService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/magazins');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(magazin: IMagazin): Observable<EntityResponseType> {
    return this.http.post<IMagazin>(this.resourceUrl, magazin, { observe: 'response' });
  }

  update(magazin: IMagazin): Observable<EntityResponseType> {
    return this.http.put<IMagazin>(`${this.resourceUrl}/${getMagazinIdentifier(magazin) as number}`, magazin, { observe: 'response' });
  }

  partialUpdate(magazin: IMagazin): Observable<EntityResponseType> {
    return this.http.patch<IMagazin>(`${this.resourceUrl}/${getMagazinIdentifier(magazin) as number}`, magazin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMagazin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMagazin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
    getMagazins(): Observable<IMagazin[]>{
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    return this.http.get<any>(`${this.resourceUrl}/getAll`)
  }
  addMagazinToCollectionIfMissing(magazinCollection: IMagazin[], ...magazinsToCheck: (IMagazin | null | undefined)[]): IMagazin[] {
    const magazins: IMagazin[] = magazinsToCheck.filter(isPresent);
    if (magazins.length > 0) {
      const magazinCollectionIdentifiers = magazinCollection.map(magazinItem => getMagazinIdentifier(magazinItem)!);
      const magazinsToAdd = magazins.filter(magazinItem => {
        const magazinIdentifier = getMagazinIdentifier(magazinItem);
        if (magazinIdentifier == null || magazinCollectionIdentifiers.includes(magazinIdentifier)) {
          return false;
        }
        magazinCollectionIdentifiers.push(magazinIdentifier);
        return true;
      });
      return [...magazinsToAdd, ...magazinCollection];
    }
    return magazinCollection;
  }
}
