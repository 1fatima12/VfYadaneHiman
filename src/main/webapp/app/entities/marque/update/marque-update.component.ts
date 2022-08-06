import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMarque, Marque } from '../marque.model';
import { MarqueService } from '../service/marque.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-marque-update',
  templateUrl: './marque-update.component.html',
})
export class MarqueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nomMarque: [],
    logo: [],
    logoContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected marqueService: MarqueService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marque }) => {
      this.updateForm(marque);
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
    const marque = this.createFromForm();
    if (marque.id !== undefined) {
      this.subscribeToSaveResponse(this.marqueService.update(marque));
    } else {
      this.subscribeToSaveResponse(this.marqueService.create(marque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarque>>): void {
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

  protected updateForm(marque: IMarque): void {
    this.editForm.patchValue({
      id: marque.id,
      nomMarque: marque.nomMarque,
      logo: marque.logo,
      logoContentType: marque.logoContentType,
    });
  }

  protected createFromForm(): IMarque {
    return {
      ...new Marque(),
      id: this.editForm.get(['id'])!.value,
      nomMarque: this.editForm.get(['nomMarque'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
    };
  }
}
