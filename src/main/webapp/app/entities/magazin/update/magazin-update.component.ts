import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMagazin, Magazin } from '../magazin.model';
import { MagazinService } from '../service/magazin.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'jhi-magazin-update',
  templateUrl: './magazin-update.component.html',
})
export class MagazinUpdateComponent implements OnInit {
  isSaving = false;

  locationsCollection: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    nomMagazin: [],
    adresseMagazin: [],
    location: [],
  });

  constructor(
    protected magazinService: MagazinService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magazin }) => {
      this.updateForm(magazin);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const magazin = this.createFromForm();
    if (magazin.id !== undefined) {
      this.subscribeToSaveResponse(this.magazinService.update(magazin));
    } else {
      this.subscribeToSaveResponse(this.magazinService.create(magazin));
    }
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMagazin>>): void {
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

  protected updateForm(magazin: IMagazin): void {
    this.editForm.patchValue({
      id: magazin.id,
      nomMagazin: magazin.nomMagazin,
      adresseMagazin: magazin.adresseMagazin,
      location: magazin.location,
    });

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsCollection, magazin.location);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'magazin-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));
  }

  protected createFromForm(): IMagazin {
    return {
      ...new Magazin(),
      id: this.editForm.get(['id'])!.value,
      nomMagazin: this.editForm.get(['nomMagazin'])!.value,
      adresseMagazin: this.editForm.get(['adresseMagazin'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }
}
