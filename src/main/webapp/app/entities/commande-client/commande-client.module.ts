import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommandeClientComponent } from './list/commande-client.component';
import { CommandeClientDetailComponent } from './detail/commande-client-detail.component';
import { CommandeClientUpdateComponent } from './update/commande-client-update.component';
import { CommandeClientDeleteDialogComponent } from './delete/commande-client-delete-dialog.component';
import { CommandeClientRoutingModule } from './route/commande-client-routing.module';

@NgModule({
  imports: [SharedModule, CommandeClientRoutingModule],
  declarations: [
    CommandeClientComponent,
    CommandeClientDetailComponent,
    CommandeClientUpdateComponent,
    CommandeClientDeleteDialogComponent,
  ],
  entryComponents: [CommandeClientDeleteDialogComponent],
})
export class CommandeClientModule {}
