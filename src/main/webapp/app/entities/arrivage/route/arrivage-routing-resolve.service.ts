import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArrivage, Arrivage } from '../arrivage.model';
import { ArrivageService } from '../service/arrivage.service';

@Injectable({ providedIn: 'root' })
export class ArrivageRoutingResolveService implements Resolve<IArrivage> {
  constructor(protected service: ArrivageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArrivage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((arrivage: HttpResponse<Arrivage>) => {
          if (arrivage.body) {
            return of(arrivage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Arrivage());
  }
}
