import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArrivageComponent } from '../list/arrivage.component';
import { ArrivageDetailComponent } from '../detail/arrivage-detail.component';
import { ArrivageUpdateComponent } from '../update/arrivage-update.component';
import { ArrivageRoutingResolveService } from './arrivage-routing-resolve.service';

const arrivageRoute: Routes = [
  {
    path: '',
    component: ArrivageComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArrivageDetailComponent,
    resolve: {
      arrivage: ArrivageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArrivageUpdateComponent,
    resolve: {
      arrivage: ArrivageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArrivageUpdateComponent,
    resolve: {
      arrivage: ArrivageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(arrivageRoute)],
  exports: [RouterModule],
})
export class ArrivageRoutingModule {}
