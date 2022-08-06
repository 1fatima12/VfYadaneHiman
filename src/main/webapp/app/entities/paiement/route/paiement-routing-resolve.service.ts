import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaiement, Paiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';

@Injectable({ providedIn: 'root' })
export class PaiementRoutingResolveService implements Resolve<IPaiement> {
  constructor(protected service: PaiementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaiement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paiement: HttpResponse<Paiement>) => {
          if (paiement.body) {
            return of(paiement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paiement());
  }
}
