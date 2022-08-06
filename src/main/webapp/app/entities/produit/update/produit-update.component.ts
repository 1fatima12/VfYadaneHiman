import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Routes } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduit, Produit } from '../produit.model';
import { ProduitService } from '../service/produit.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IMarque } from 'app/entities/marque/marque.model';
import { MarqueService } from 'app/entities/marque/service/marque.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})

export class ProduitUpdateComponent implements OnInit {
  isSaving = false;

  categoriesSharedCollection: ICategorie[] = [];
  marquesSharedCollection: IMarque[] = [];

  editForm = this.fb.group({
    id: [],
    numProd: [],
    nomProd: [],
    status: [],
    prixVente: [],
    image: [],
    imageContentType: [],
    categorie: [],
    marque: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected produitService: ProduitService,
    protected categorieService: CategorieService,
    protected marqueService: MarqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('yadaneApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  trackCategorieById(_index: number, item: ICategorie): number {
    return item.id!;
  }

  trackMarqueById(_index: number, item: IMarque): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  protected updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      numProd: produit.numProd,
      nomProd: produit.nomProd,
      status: produit.status,
      prixVente: produit.prixVente,
      image: produit.image,
      imageContentType: produit.imageContentType,
      categorie: produit.categorie,
      marque: produit.marque,
    });

    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing(
      this.categoriesSharedCollection,
      produit.categorie
    );
    this.marquesSharedCollection = this.marqueService.addMarqueToCollectionIfMissing(this.marquesSharedCollection, produit.marque);
  }

  protected loadRelationshipsOptions(): void {
    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing(categories, this.editForm.get('categorie')!.value)
        )
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));

    this.marqueService
      .query()
      .pipe(map((res: HttpResponse<IMarque[]>) => res.body ?? []))
      .pipe(map((marques: IMarque[]) => this.marqueService.addMarqueToCollectionIfMissing(marques, this.editForm.get('marque')!.value)))
      .subscribe((marques: IMarque[]) => (this.marquesSharedCollection = marques));
  }

  protected createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      numProd: this.editForm.get(['numProd'])!.value,
      nomProd: this.editForm.get(['nomProd'])!.value,
      status: this.editForm.get(['status'])!.value,
      prixVente: this.editForm.get(['prixVente'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      marque: this.editForm.get(['marque'])!.value,
    };
  }
}
