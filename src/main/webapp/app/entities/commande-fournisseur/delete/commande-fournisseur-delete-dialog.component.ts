import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeFournisseur } from '../commande-fournisseur.model';
import { CommandeFournisseurService } from '../service/commande-fournisseur.service';

@Component({
  templateUrl: './commande-fournisseur-delete-dialog.component.html',
})
export class CommandeFournisseurDeleteDialogComponent {
  commandeFournisseur?: ICommandeFournisseur;

  constructor(protected commandeFournisseurService: CommandeFournisseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeFournisseurService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
