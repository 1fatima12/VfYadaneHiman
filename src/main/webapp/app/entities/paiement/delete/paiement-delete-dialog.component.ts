import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';

@Component({
  templateUrl: './paiement-delete-dialog.component.html',
})
export class PaiementDeleteDialogComponent {
  paiement?: IPaiement;

  constructor(protected paiementService: PaiementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paiementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
