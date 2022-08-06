import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmploye, Employe } from '../employe.model';
import { EmployeService } from '../service/employe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;

  locationsCollection: ILocation[] = [];
  magazinsSharedCollection: IMagazin[] = [];

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
    poste: [],
    salaire: [],
    dateEmbauche: [],
    location: [],
    magzin: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected employeService: EmployeService,
    protected locationService: LocationService,
    protected magazinService: MagazinService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.updateForm(employe);

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
    const employe = this.createFromForm();
    if (employe.id !== undefined) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  trackMagazinById(_index: number, item: IMagazin): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
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

  protected updateForm(employe: IEmploye): void {
    this.editForm.patchValue({
      id: employe.id,
      nom: employe.nom,
      prenom: employe.prenom,
      telephone: employe.telephone,
      email: employe.email,
      adresse: employe.adresse,
      age: employe.age,
      photo: employe.photo,
      photoContentType: employe.photoContentType,
      poste: employe.poste,
      salaire: employe.salaire,
      dateEmbauche: employe.dateEmbauche,
      location: employe.location,
      magzin: employe.magzin,
    });

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsCollection, employe.location);
    this.magazinsSharedCollection = this.magazinService.addMagazinToCollectionIfMissing(this.magazinsSharedCollection, employe.magzin);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'employe-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));

    this.magazinService
      .query()
      .pipe(map((res: HttpResponse<IMagazin[]>) => res.body ?? []))
      .pipe(
        map((magazins: IMagazin[]) => this.magazinService.addMagazinToCollectionIfMissing(magazins, this.editForm.get('magzin')!.value))
      )
      .subscribe((magazins: IMagazin[]) => (this.magazinsSharedCollection = magazins));
  }

  protected createFromForm(): IEmploye {
    return {
      ...new Employe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      email: this.editForm.get(['email'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      age: this.editForm.get(['age'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      poste: this.editForm.get(['poste'])!.value,
      salaire: this.editForm.get(['salaire'])!.value,
      dateEmbauche: this.editForm.get(['dateEmbauche'])!.value,
      location: this.editForm.get(['location'])!.value,
      magzin: this.editForm.get(['magzin'])!.value,
    };
  }
}
