import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmploye, Employe } from '../employe.model';
import { EmployeService } from '../service/employe.service';

@Injectable({ providedIn: 'root' })
export class EmployeRoutingResolveService implements Resolve<IEmploye> {
  constructor(protected service: EmployeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmploye> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employe: HttpResponse<Employe>) => {
          if (employe.body) {
            return of(employe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Employe());
  }
}
