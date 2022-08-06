import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommandeFournisseur, getCommandeFournisseurIdentifier } from '../commande-fournisseur.model';

export type EntityResponseType = HttpResponse<ICommandeFournisseur>;
export type EntityArrayResponseType = HttpResponse<ICommandeFournisseur[]>;

@Injectable({ providedIn: 'root' })
export class CommandeFournisseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commande-fournisseurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commandeFournisseur: ICommandeFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeFournisseur);
    return this.http
      .post<ICommandeFournisseur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeFournisseur: ICommandeFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeFournisseur);
    return this.http
      .put<ICommandeFournisseur>(`${this.resourceUrl}/${getCommandeFournisseurIdentifier(commandeFournisseur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(commandeFournisseur: ICommandeFournisseur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeFournisseur);
    return this.http
      .patch<ICommandeFournisseur>(`${this.resourceUrl}/${getCommandeFournisseurIdentifier(commandeFournisseur) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeFournisseur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeFournisseur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommandeFournisseurToCollectionIfMissing(
    commandeFournisseurCollection: ICommandeFournisseur[],
    ...commandeFournisseursToCheck: (ICommandeFournisseur | null | undefined)[]
  ): ICommandeFournisseur[] {
    const commandeFournisseurs: ICommandeFournisseur[] = commandeFournisseursToCheck.filter(isPresent);
    if (commandeFournisseurs.length > 0) {
      const commandeFournisseurCollectionIdentifiers = commandeFournisseurCollection.map(
        commandeFournisseurItem => getCommandeFournisseurIdentifier(commandeFournisseurItem)!
      );
      const commandeFournisseursToAdd = commandeFournisseurs.filter(commandeFournisseurItem => {
        const commandeFournisseurIdentifier = getCommandeFournisseurIdentifier(commandeFournisseurItem);
        if (commandeFournisseurIdentifier == null || commandeFournisseurCollectionIdentifiers.includes(commandeFournisseurIdentifier)) {
          return false;
        }
        commandeFournisseurCollectionIdentifiers.push(commandeFournisseurIdentifier);
        return true;
      });
      return [...commandeFournisseursToAdd, ...commandeFournisseurCollection];
    }
    return commandeFournisseurCollection;
  }

  protected convertDateFromClient(commandeFournisseur: ICommandeFournisseur): ICommandeFournisseur {
    return Object.assign({}, commandeFournisseur, {
      dateCom: commandeFournisseur.dateCom?.isValid() ? commandeFournisseur.dateCom.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCom = res.body.dateCom ? dayjs(res.body.dateCom) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeFournisseur: ICommandeFournisseur) => {
        commandeFournisseur.dateCom = commandeFournisseur.dateCom ? dayjs(commandeFournisseur.dateCom) : undefined;
      });
    }
    return res;
  }
}
