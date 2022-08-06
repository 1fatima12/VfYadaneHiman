import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeClient } from '../commande-client.model';

@Component({
  selector: 'jhi-commande-client-detail',
  templateUrl: './commande-client-detail.component.html',
})
export class CommandeClientDetailComponent implements OnInit {
  commandeClient: ICommandeClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeClient }) => {
      this.commandeClient = commandeClient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
