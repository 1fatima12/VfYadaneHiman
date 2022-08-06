import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MarqueComponent } from '../list/marque.component';
import { MarqueDetailComponent } from '../detail/marque-detail.component';
import { MarqueUpdateComponent } from '../update/marque-update.component';
import { MarqueRoutingResolveService } from './marque-routing-resolve.service';

const marqueRoute: Routes = [
  {
    path: '',
    component: MarqueComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MarqueDetailComponent,
    resolve: {
      marque: MarqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MarqueUpdateComponent,
    resolve: {
      marque: MarqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MarqueUpdateComponent,
    resolve: {
      marque: MarqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(marqueRoute)],
  exports: [RouterModule],
})
export class MarqueRoutingModule {}
