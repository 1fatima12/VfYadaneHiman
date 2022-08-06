import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BtiComponent } from './list/bti.component';
import { BtiDetailComponent } from './detail/bti-detail.component';
import { BtiUpdateComponent } from './update/bti-update.component';
import { BtiDeleteDialogComponent } from './delete/bti-delete-dialog.component';
import { BtiRoutingModule } from './route/bti-routing.module';

@NgModule({
  imports: [SharedModule, BtiRoutingModule],
  declarations: [BtiComponent, BtiDetailComponent, BtiUpdateComponent, BtiDeleteDialogComponent],
  entryComponents: [BtiDeleteDialogComponent],
})
export class BtiModule {}
