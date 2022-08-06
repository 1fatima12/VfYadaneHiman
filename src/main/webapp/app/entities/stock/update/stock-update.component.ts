import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStock, Stock } from '../stock.model';
import { StockService } from '../service/stock.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html',
})
export class StockUpdateComponent implements OnInit {
  isSaving = false;

  produitsSharedCollection: IProduit[] = [];
  magazinsSharedCollection: IMagazin[] = [];

  editForm = this.fb.group({
    id: [],
    qte: [],
    produit: [],
    magazin: [],
  });

  constructor(
    protected stockService: StockService,
    protected produitService: ProduitService,
    protected magazinService: MagazinService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.updateForm(stock);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stock = this.createFromForm();
    if (stock.id !== undefined) {
      this.subscribeToSaveResponse(this.stockService.update(stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.create(stock));
    }
  }

  trackProduitById(_index: number, item: IProduit): number {
    return item.id!;
  }

  trackMagazinById(_index: number, item: IMagazin): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>): void {
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

  protected updateForm(stock: IStock): void {
    this.editForm.patchValue({
      id: stock.id,
      qte: stock.qte,
      produit: stock.produit,
      magazin: stock.magazin,
    });

    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(this.produitsSharedCollection, stock.produit);
    this.magazinsSharedCollection = this.magazinService.addMagazinToCollectionIfMissing(this.magazinsSharedCollection, stock.magazin);
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

  protected createFromForm(): IStock {
    return {
      ...new Stock(),
      id: this.editForm.get(['id'])!.value,
      qte: this.editForm.get(['qte'])!.value,
      produit: this.editForm.get(['produit'])!.value,
      magazin: this.editForm.get(['magazin'])!.value,
    };
  }
}
