import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FournisseurComponent } from '../list/fournisseur.component';
import { FournisseurDetailComponent } from '../detail/fournisseur-detail.component';
import { FournisseurUpdateComponent } from '../update/fournisseur-update.component';
import { FournisseurRoutingResolveService } from './fournisseur-routing-resolve.service';

const fournisseurRoute: Routes = [
  {
    path: '',
    component: FournisseurComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FournisseurDetailComponent,
    resolve: {
      fournisseur: FournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FournisseurUpdateComponent,
    resolve: {
      fournisseur: FournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FournisseurUpdateComponent,
    resolve: {
      fournisseur: FournisseurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fournisseurRoute)],
  exports: [RouterModule],
})
export class FournisseurRoutingModule {}
