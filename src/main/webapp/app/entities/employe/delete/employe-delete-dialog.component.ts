import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploye } from '../employe.model';
import { EmployeService } from '../service/employe.service';

@Component({
  templateUrl: './employe-delete-dialog.component.html',
})
export class EmployeDeleteDialogComponent {
  employe?: IEmploye;

  constructor(protected employeService: EmployeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
