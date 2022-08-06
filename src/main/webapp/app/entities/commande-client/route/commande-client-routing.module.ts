import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommandeClientComponent } from '../list/commande-client.component';
import { CommandeClientDetailComponent } from '../detail/commande-client-detail.component';
import { CommandeClientUpdateComponent } from '../update/commande-client-update.component';
import { CommandeClientRoutingResolveService } from './commande-client-routing-resolve.service';

const commandeClientRoute: Routes = [
  {
    path: '',
    component: CommandeClientComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommandeClientDetailComponent,
    resolve: {
      commandeClient: CommandeClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommandeClientUpdateComponent,
    resolve: {
      commandeClient: CommandeClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommandeClientUpdateComponent,
    resolve: {
      commandeClient: CommandeClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commandeClientRoute)],
  exports: [RouterModule],
})
export class CommandeClientRoutingModule {}
