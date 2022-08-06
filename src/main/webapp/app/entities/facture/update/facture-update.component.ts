import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFacture, Facture } from '../facture.model';
import { FactureService } from '../service/facture.service';
import { IArrivage } from 'app/entities/arrivage/arrivage.model';
import { ArrivageService } from 'app/entities/arrivage/service/arrivage.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html',
})
export class FactureUpdateComponent implements OnInit {
  isSaving = false;

  arrivagesCollection: IArrivage[] = [];

  editForm = this.fb.group({
    id: [],
    idFacture: [],
    montant: [],
    arrivage: [],
  });

  constructor(
    protected factureService: FactureService,
    protected arrivageService: ArrivageService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facture }) => {
      this.updateForm(facture);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  trackArrivageById(_index: number, item: IArrivage): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>): void {
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

  protected updateForm(facture: IFacture): void {
    this.editForm.patchValue({
      id: facture.id,
      idFacture: facture.idFacture,
      montant: facture.montant,
      arrivage: facture.arrivage,
    });

    this.arrivagesCollection = this.arrivageService.addArrivageToCollectionIfMissing(this.arrivagesCollection, facture.arrivage);
  }

  protected loadRelationshipsOptions(): void {
    this.arrivageService
      .query({ filter: 'facture-is-null' })
      .pipe(map((res: HttpResponse<IArrivage[]>) => res.body ?? []))
      .pipe(
        map((arrivages: IArrivage[]) =>
          this.arrivageService.addArrivageToCollectionIfMissing(arrivages, this.editForm.get('arrivage')!.value)
        )
      )
      .subscribe((arrivages: IArrivage[]) => (this.arrivagesCollection = arrivages));
  }

  protected createFromForm(): IFacture {
    return {
      ...new Facture(),
      id: this.editForm.get(['id'])!.value,
      idFacture: this.editForm.get(['idFacture'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      arrivage: this.editForm.get(['arrivage'])!.value,
    };
  }
}
