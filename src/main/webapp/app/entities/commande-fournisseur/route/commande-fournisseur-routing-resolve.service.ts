import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommandeFournisseur, CommandeFournisseur } from '../commande-fournisseur.model';
import { CommandeFournisseurService } from '../service/commande-fournisseur.service';

@Injectable({ providedIn: 'root' })
export class CommandeFournisseurRoutingResolveService implements Resolve<ICommandeFournisseur> {
  constructor(protected service: CommandeFournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commandeFournisseur: HttpResponse<CommandeFournisseur>) => {
          if (commandeFournisseur.body) {
            return of(commandeFournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeFournisseur());
  }
}
