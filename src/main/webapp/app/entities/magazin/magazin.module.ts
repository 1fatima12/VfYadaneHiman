import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MagazinComponent } from './list/magazin.component';
import { MagazinDetailComponent } from './detail/magazin-detail.component';
import { MagazinUpdateComponent } from './update/magazin-update.component';
import { MagazinDeleteDialogComponent } from './delete/magazin-delete-dialog.component';
import { MagazinRoutingModule } from './route/magazin-routing.module';

@NgModule({
  imports: [SharedModule, MagazinRoutingModule],
  declarations: [MagazinComponent, MagazinDetailComponent, MagazinUpdateComponent, MagazinDeleteDialogComponent],
  entryComponents: [MagazinDeleteDialogComponent],
})
export class MagazinModule {}
