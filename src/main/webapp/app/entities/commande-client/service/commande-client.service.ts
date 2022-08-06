import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommandeClient, getCommandeClientIdentifier } from '../commande-client.model';

export type EntityResponseType = HttpResponse<ICommandeClient>;
export type EntityArrayResponseType = HttpResponse<ICommandeClient[]>;

@Injectable({ providedIn: 'root' })
export class CommandeClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commande-clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commandeClient: ICommandeClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeClient);
    return this.http
      .post<ICommandeClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeClient: ICommandeClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeClient);
    return this.http
      .put<ICommandeClient>(`${this.resourceUrl}/${getCommandeClientIdentifier(commandeClient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(commandeClient: ICommandeClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeClient);
    return this.http
      .patch<ICommandeClient>(`${this.resourceUrl}/${getCommandeClientIdentifier(commandeClient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeClient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeClient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCommandeClientToCollectionIfMissing(
    commandeClientCollection: ICommandeClient[],
    ...commandeClientsToCheck: (ICommandeClient | null | undefined)[]
  ): ICommandeClient[] {
    const commandeClients: ICommandeClient[] = commandeClientsToCheck.filter(isPresent);
    if (commandeClients.length > 0) {
      const commandeClientCollectionIdentifiers = commandeClientCollection.map(
        commandeClientItem => getCommandeClientIdentifier(commandeClientItem)!
      );
      const commandeClientsToAdd = commandeClients.filter(commandeClientItem => {
        const commandeClientIdentifier = getCommandeClientIdentifier(commandeClientItem);
        if (commandeClientIdentifier == null || commandeClientCollectionIdentifiers.includes(commandeClientIdentifier)) {
          return false;
        }
        commandeClientCollectionIdentifiers.push(commandeClientIdentifier);
        return true;
      });
      return [...commandeClientsToAdd, ...commandeClientCollection];
    }
    return commandeClientCollection;
  }

  protected convertDateFromClient(commandeClient: ICommandeClient): ICommandeClient {
    return Object.assign({}, commandeClient, {
      dateCom: commandeClient.dateCom?.isValid() ? commandeClient.dateCom.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((commandeClient: ICommandeClient) => {
        commandeClient.dateCom = commandeClient.dateCom ? dayjs(commandeClient.dateCom) : undefined;
      });
    }
    return res;
  }
}
