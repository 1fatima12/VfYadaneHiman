import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMagazin } from '../magazin.model';
import { MagazinService } from '../service/magazin.service';

@Component({
  templateUrl: './magazin-delete-dialog.component.html',
})
export class MagazinDeleteDialogComponent {
  magazin?: IMagazin;

  constructor(protected magazinService: MagazinService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.magazinService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
