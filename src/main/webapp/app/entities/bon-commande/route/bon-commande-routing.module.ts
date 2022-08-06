import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BonCommandeComponent } from '../list/bon-commande.component';
import { BonCommandeDetailComponent } from '../detail/bon-commande-detail.component';
import { BonCommandeUpdateComponent } from '../update/bon-commande-update.component';
import { BonCommandeRoutingResolveService } from './bon-commande-routing-resolve.service';

const bonCommandeRoute: Routes = [
  {
    path: '',
    component: BonCommandeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BonCommandeDetailComponent,
    resolve: {
      bonCommande: BonCommandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BonCommandeUpdateComponent,
    resolve: {
      bonCommande: BonCommandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BonCommandeUpdateComponent,
    resolve: {
      bonCommande: BonCommandeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bonCommandeRoute)],
  exports: [RouterModule],
})
export class BonCommandeRoutingModule {}
