import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArrivageComponent } from './list/arrivage.component';
import { ArrivageDetailComponent } from './detail/arrivage-detail.component';
import { ArrivageUpdateComponent } from './update/arrivage-update.component';
import { ArrivageDeleteDialogComponent } from './delete/arrivage-delete-dialog.component';
import { ArrivageRoutingModule } from './route/arrivage-routing.module';

@NgModule({
  imports: [SharedModule, ArrivageRoutingModule],
  declarations: [ArrivageComponent, ArrivageDetailComponent, ArrivageUpdateComponent, ArrivageDeleteDialogComponent],
  entryComponents: [ArrivageDeleteDialogComponent],
})
export class ArrivageModule {}
