import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MarqueComponent } from './list/marque.component';
import { MarqueDetailComponent } from './detail/marque-detail.component';
import { MarqueUpdateComponent } from './update/marque-update.component';
import { MarqueDeleteDialogComponent } from './delete/marque-delete-dialog.component';
import { MarqueRoutingModule } from './route/marque-routing.module';

@NgModule({
  imports: [SharedModule, MarqueRoutingModule],
  declarations: [MarqueComponent, MarqueDetailComponent, MarqueUpdateComponent, MarqueDeleteDialogComponent],
  entryComponents: [MarqueDeleteDialogComponent],
})
export class MarqueModule {}
