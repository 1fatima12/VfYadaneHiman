import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MagazinComponent } from '../list/magazin.component';
import { MagazinDetailComponent } from '../detail/magazin-detail.component';
import { MagazinUpdateComponent } from '../update/magazin-update.component';
import { MagazinRoutingResolveService } from './magazin-routing-resolve.service';

const magazinRoute: Routes = [
  {
    path: '',
    component: MagazinComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MagazinDetailComponent,
    resolve: {
      magazin: MagazinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MagazinUpdateComponent,
    resolve: {
      magazin: MagazinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MagazinUpdateComponent,
    resolve: {
      magazin: MagazinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(magazinRoute)],
  exports: [RouterModule],
})
export class MagazinRoutingModule {}
