import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IArrivage, Arrivage } from '../arrivage.model';
import { ArrivageService } from '../service/arrivage.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';

@Component({
  selector: 'jhi-arrivage-update',
  templateUrl: './arrivage-update.component.html',
})
export class ArrivageUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  fournisseursSharedCollection: IFournisseur[] = [];

  editForm = this.fb.group({
    id: [],
    dateArrivage: [],
    prixAchat: [],
    produit: [],
    fournisseur: [],
  });

  constructor(
    protected arrivageService: ArrivageService,
    protected produitService: ProduitService,
    protected fournisseurService: FournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrivage }) => {
      this.updateForm(arrivage);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arrivage = this.createFromForm();
    if (arrivage.id !== undefined) {
      this.subscribeToSaveResponse(this.arrivageService.update(arrivage));
    } else {
      this.subscribeToSaveResponse(this.arrivageService.create(arrivage));
    }
  }

  trackProduitById(_index: number, item: IProduit): number {
    return item.id!;
  }

  trackFournisseurById(_index: number, item: IFournisseur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArrivage>>): void {
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

  protected updateForm(arrivage: IArrivage): void {
    this.editForm.patchValue({
      id: arrivage.id,
      dateArrivage: arrivage.dateArrivage,
      prixAchat: arrivage.prixAchat,
      produit: arrivage.produit,
      fournisseur: arrivage.fournisseur,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(this.produitsSharedCollection, arrivage.produit);
    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      arrivage.fournisseur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing(produits, this.editForm.get('produit')!.value))
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));

    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));
  }

  protected createFromForm(): IArrivage {
    return {
      ...new Arrivage(),
      id: this.editForm.get(['id'])!.value,
      dateArrivage: this.editForm.get(['dateArrivage'])!.value,
      prixAchat: this.editForm.get(['prixAchat'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
    };
  }
}
