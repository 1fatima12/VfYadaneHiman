import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBonCommande, BonCommande } from '../bon-commande.model';
import { BonCommandeService } from '../service/bon-commande.service';

@Injectable({ providedIn: 'root' })
export class BonCommandeRoutingResolveService implements Resolve<IBonCommande> {
  constructor(protected service: BonCommandeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonCommande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bonCommande: HttpResponse<BonCommande>) => {
          if (bonCommande.body) {
            return of(bonCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BonCommande());
  }
}
