import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBti, Bti } from '../bti.model';
import { BtiService } from '../service/bti.service';

@Injectable({ providedIn: 'root' })
export class BtiRoutingResolveService implements Resolve<IBti> {
  constructor(protected service: BtiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBti> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bti: HttpResponse<Bti>) => {
          if (bti.body) {
            return of(bti.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bti());
  }
}
