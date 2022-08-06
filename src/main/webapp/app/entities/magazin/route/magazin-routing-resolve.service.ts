import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMagazin, Magazin } from '../magazin.model';
import { MagazinService } from '../service/magazin.service';

@Injectable({ providedIn: 'root' })
export class MagazinRoutingResolveService implements Resolve<IMagazin> {
  constructor(protected service: MagazinService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMagazin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((magazin: HttpResponse<Magazin>) => {
          if (magazin.body) {
            return of(magazin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Magazin());
  }
}
