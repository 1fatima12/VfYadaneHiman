import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMarque, Marque } from '../marque.model';
import { MarqueService } from '../service/marque.service';

@Injectable({ providedIn: 'root' })
export class MarqueRoutingResolveService implements Resolve<IMarque> {
  constructor(protected service: MarqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMarque> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((marque: HttpResponse<Marque>) => {
          if (marque.body) {
            return of(marque.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Marque());
  }
}
