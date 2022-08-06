import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BtiComponent } from '../list/bti.component';
import { BtiDetailComponent } from '../detail/bti-detail.component';
import { BtiUpdateComponent } from '../update/bti-update.component';
import { BtiRoutingResolveService } from './bti-routing-resolve.service';

const btiRoute: Routes = [
  {
    path: '',
    component: BtiComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BtiDetailComponent,
    resolve: {
      bti: BtiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BtiUpdateComponent,
    resolve: {
      bti: BtiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BtiUpdateComponent,
    resolve: {
      bti: BtiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(btiRoute)],
  exports: [RouterModule],
})
export class BtiRoutingModule {}
