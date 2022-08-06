import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';

@Component({
  templateUrl: './commande-client-delete-dialog.component.html',
})
export class CommandeClientDeleteDialogComponent {
  commandeClient?: ICommandeClient;

  constructor(protected commandeClientService: CommandeClientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeClientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
