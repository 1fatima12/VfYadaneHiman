import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommandeFournisseurComponent } from '../list/commande-fournisseur.component';
import { CommandeFournisseurDetailComponent } from '../detail/commande-fournisseur-detail.component';
import { CommandeFournisseurUpdateComponent } from '../update/commande-fournisseur-update.component';
import { CommandeFournisseurRoutingResolveService } from './commande-fournisseur-routing-resolve.service';

const commandeFournisseurRoute: Routes = [
  {
    path: '',
    component: CommandeFournisseurComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommandeFournisseurDetailComponent,
    resolve: {
      commandeFournisseur: CommandeFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommandeFournisseurUpdateComponent,
    resolve: {
      commandeFournisseur: CommandeFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommandeFournisseurUpdateComponent,
    resolve: {
      commandeFournisseur: CommandeFournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commandeFournisseurRoute)],
  exports: [RouterModule],
})
export class CommandeFournisseurRoutingModule {}
