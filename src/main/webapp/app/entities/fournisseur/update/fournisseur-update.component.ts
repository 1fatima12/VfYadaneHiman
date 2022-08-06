import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFournisseur, Fournisseur } from '../fournisseur.model';
import { FournisseurService } from '../service/fournisseur.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'jhi-fournisseur-update',
  templateUrl: './fournisseur-update.component.html',
})
export class FournisseurUpdateComponent implements OnInit {
  isSaving = false;

  locationsCollection: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    telephone: [],
    email: [],
    adresse: [],
    age: [],
    photo: [],
    photoContentType: [],
    entreprise: [],
    fax: [],
    ice: [],
    location: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fournisseurService: FournisseurService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fournisseur }) => {
      this.updateForm(fournisseur);

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
    const fournisseur = this.createFromForm();
    if (fournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.fournisseurService.update(fournisseur));
    } else {
      this.subscribeToSaveResponse(this.fournisseurService.create(fournisseur));
    }
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFournisseur>>): void {
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

  protected updateForm(fournisseur: IFournisseur): void {
    this.editForm.patchValue({
      id: fournisseur.id,
      nom: fournisseur.nom,
      prenom: fournisseur.prenom,
      telephone: fournisseur.telephone,
      email: fournisseur.email,
      adresse: fournisseur.adresse,
      age: fournisseur.age,
      photo: fournisseur.photo,
      photoContentType: fournisseur.photoContentType,
      entreprise: fournisseur.entreprise,
      fax: fournisseur.fax,
      ice: fournisseur.ice,
      location: fournisseur.location,
    });

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsCollection, fournisseur.location);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'fournisseur-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));
  }

  protected createFromForm(): IFournisseur {
    return {
      ...new Fournisseur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      age: this.editForm.get(['age'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      ice: this.editForm.get(['ice'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }
}
