import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IArrivage } from '../arrivage.model';
import { ArrivageService } from '../service/arrivage.service';

@Component({
  templateUrl: './arrivage-delete-dialog.component.html',
})
export class ArrivageDeleteDialogComponent {
  arrivage?: IArrivage;

  constructor(protected arrivageService: ArrivageService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arrivageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
