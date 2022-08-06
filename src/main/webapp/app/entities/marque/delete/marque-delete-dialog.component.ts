import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMarque } from '../marque.model';
import { MarqueService } from '../service/marque.service';

@Component({
  templateUrl: './marque-delete-dialog.component.html',
})
export class MarqueDeleteDialogComponent {
  marque?: IMarque;

  constructor(protected marqueService: MarqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.marqueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
