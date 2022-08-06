import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommandeClient, CommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';

@Injectable({ providedIn: 'root' })
export class CommandeClientRoutingResolveService implements Resolve<ICommandeClient> {
  constructor(protected service: CommandeClientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeClient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commandeClient: HttpResponse<CommandeClient>) => {
          if (commandeClient.body) {
            return of(commandeClient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeClient());
  }
}
