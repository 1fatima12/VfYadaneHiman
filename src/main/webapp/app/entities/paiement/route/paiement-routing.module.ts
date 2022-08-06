import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaiementComponent } from '../list/paiement.component';
import { PaiementDetailComponent } from '../detail/paiement-detail.component';
import { PaiementUpdateComponent } from '../update/paiement-update.component';
import { PaiementRoutingResolveService } from './paiement-routing-resolve.service';

const paiementRoute: Routes = [
  {
    path: '',
    component: PaiementComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaiementDetailComponent,
    resolve: {
      paiement: PaiementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaiementUpdateComponent,
    resolve: {
      paiement: PaiementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paiementRoute)],
  exports: [RouterModule],
})
export class PaiementRoutingModule {}
