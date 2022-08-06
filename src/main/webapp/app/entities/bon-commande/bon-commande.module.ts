import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BonCommandeComponent } from './list/bon-commande.component';
import { BonCommandeDetailComponent } from './detail/bon-commande-detail.component';
import { BonCommandeUpdateComponent } from './update/bon-commande-update.component';
import { BonCommandeDeleteDialogComponent } from './delete/bon-commande-delete-dialog.component';
import { BonCommandeRoutingModule } from './route/bon-commande-routing.module';
import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';


@NgModule({
  imports: [SharedModule, BonCommandeRoutingModule,MatSliderModule,MatInputModule,MatFormFieldModule,MatAutocompleteModule
   ],
  declarations: [BonCommandeComponent, BonCommandeDetailComponent, BonCommandeUpdateComponent, BonCommandeDeleteDialogComponent],
  entryComponents: [BonCommandeDeleteDialogComponent],
})
export class BonCommandeModule {}
