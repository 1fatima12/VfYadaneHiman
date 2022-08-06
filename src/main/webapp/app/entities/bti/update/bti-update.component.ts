import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBti, Bti } from '../bti.model';
import { BtiService } from '../service/bti.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

@Component({
  selector: 'jhi-bti-update',
  templateUrl: './bti-update.component.html',
})
export class BtiUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  magazinsSharedCollection: IMagazin[] = [];

  editForm = this.fb.group({
    id: [],
    numOrdre: [],
    date: [],
    ref: [],
    qte: [],
    produit: [],
    magazin: [],
  });

  constructor(
    protected btiService: BtiService,
    protected produitService: ProduitService,
    protected magazinService: MagazinService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bti }) => {
      this.updateForm(bti);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bti = this.createFromForm();
    if (bti.id !== undefined) {
      this.subscribeToSaveResponse(this.btiService.update(bti));
    } else {
      this.subscribeToSaveResponse(this.btiService.create(bti));
    }
  }

  trackProduitById(_index: number, item: IProduit): number {
    return item.id!;
  }

  trackMagazinById(_index: number, item: IMagazin): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBti>>): void {
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

  protected updateForm(bti: IBti): void {
    this.editForm.patchValue({
      id: bti.id,
      numOrdre: bti.numOrdre,
      date: bti.date,
      ref: bti.ref,
      qte: bti.qte,
      produit: bti.produit,
      magazin: bti.magazin,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(this.produitsSharedCollection, bti.produit);
    this.magazinsSharedCollection = this.magazinService.addMagazinToCollectionIfMissing(this.magazinsSharedCollection, bti.magazin);
  }

  protected loadRelationshipsOptions(): void {
    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing(produits, this.editForm.get('produit')!.value))
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));

    this.magazinService
      .query()
      .pipe(map((res: HttpResponse<IMagazin[]>) => res.body ?? []))
      .pipe(
        map((magazins: IMagazin[]) => this.magazinService.addMagazinToCollectionIfMissing(magazins, this.editForm.get('magazin')!.value))
      )
      .subscribe((magazins: IMagazin[]) => (this.magazinsSharedCollection = magazins));
  }

  protected createFromForm(): IBti {
    return {
      ...new Bti(),
      id: this.editForm.get(['id'])!.value,
      numOrdre: this.editForm.get(['numOrdre'])!.value,
      date: this.editForm.get(['date'])!.value,
      ref: this.editForm.get(['ref'])!.value,
      qte: this.editForm.get(['qte'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      magazin: this.editForm.get(['magazin'])!.value,
    };
  }
}
