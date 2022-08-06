import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FournisseurComponent } from './list/fournisseur.component';
import { FournisseurDetailComponent } from './detail/fournisseur-detail.component';
import { FournisseurUpdateComponent } from './update/fournisseur-update.component';
import { FournisseurDeleteDialogComponent } from './delete/fournisseur-delete-dialog.component';
import { FournisseurRoutingModule } from './route/fournisseur-routing.module';

@NgModule({
  imports: [SharedModule, FournisseurRoutingModule],
  declarations: [FournisseurComponent, FournisseurDetailComponent, FournisseurUpdateComponent, FournisseurDeleteDialogComponent],
  entryComponents: [FournisseurDeleteDialogComponent],
})
export class FournisseurModule {}
