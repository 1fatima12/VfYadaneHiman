import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFournisseur, Fournisseur } from '../fournisseur.model';
import { FournisseurService } from '../service/fournisseur.service';

@Injectable({ providedIn: 'root' })
export class FournisseurRoutingResolveService implements Resolve<IFournisseur> {
  constructor(protected service: FournisseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFournisseur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fournisseur: HttpResponse<Fournisseur>) => {
          if (fournisseur.body) {
            return of(fournisseur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fournisseur());
  }
}
