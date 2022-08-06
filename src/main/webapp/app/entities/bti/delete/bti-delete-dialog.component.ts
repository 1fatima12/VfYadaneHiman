import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBti } from '../bti.model';
import { BtiService } from '../service/bti.service';

@Component({
  templateUrl: './bti-delete-dialog.component.html',
})
export class BtiDeleteDialogComponent {
  bti?: IBti;

  constructor(protected btiService: BtiService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.btiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
