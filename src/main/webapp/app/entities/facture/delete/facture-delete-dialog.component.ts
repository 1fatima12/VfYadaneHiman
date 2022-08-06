import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacture } from '../facture.model';
import { FactureService } from '../service/facture.service';

@Component({
  templateUrl: './facture-delete-dialog.component.html',
})
export class FactureDeleteDialogComponent {
  facture?: IFacture;

  constructor(protected factureService: FactureService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
