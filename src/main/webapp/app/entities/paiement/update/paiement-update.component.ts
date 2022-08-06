import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaiement, Paiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';
import { ModePaiement } from 'app/entities/enumerations/mode-paiement.model';

@Component({
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html',
})
export class PaiementUpdateComponent implements OnInit {
  isSaving = false;
  modePaiementValues = Object.keys(ModePaiement);

  editForm = this.fb.group({
    id: [],
    type: [],
    avance: [],
    etat: [],
  });

  constructor(protected paiementService: PaiementService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.updateForm(paiement);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiement = this.createFromForm();
    if (paiement.id !== undefined) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paiement: IPaiement): void {
    this.editForm.patchValue({
      id: paiement.id,
      type: paiement.type,
      avance: paiement.avance,
      etat: paiement.etat,
    });
  }

  protected createFromForm(): IPaiement {
    return {
      ...new Paiement(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      avance: this.editForm.get(['avance'])!.value,
      etat: this.editForm.get(['etat'])!.value,
    };
  }
}
