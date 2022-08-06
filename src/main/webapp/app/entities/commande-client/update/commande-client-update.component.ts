import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICommandeClient, CommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

@Component({
  selector: 'jhi-commande-client-update',
  templateUrl: './commande-client-update.component.html',
})
export class CommandeClientUpdateComponent implements OnInit {
  isSaving = false;

  clientsSharedCollection: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    dateCom: [],
    designation: [],
    client: [],
  });

  constructor(
    protected commandeClientService: CommandeClientService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeClient }) => {
      this.updateForm(commandeClient);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeClient = this.createFromForm();
    if (commandeClient.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeClientService.update(commandeClient));
    } else {
      this.subscribeToSaveResponse(this.commandeClientService.create(commandeClient));
    }
  }

  trackClientById(_index: number, item: IClient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeClient>>): void {
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

  protected updateForm(commandeClient: ICommandeClient): void {
    this.editForm.patchValue({
      id: commandeClient.id,
      dateCom: commandeClient.dateCom,
      designation: commandeClient.designation,
      client: commandeClient.client,
    });

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing(this.clientsSharedCollection, commandeClient.client);
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing(clients, this.editForm.get('client')!.value)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }

  protected createFromForm(): ICommandeClient {
    return {
      ...new CommandeClient(),
      id: this.editForm.get(['id'])!.value,
      dateCom: this.editForm.get(['dateCom'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }
}
