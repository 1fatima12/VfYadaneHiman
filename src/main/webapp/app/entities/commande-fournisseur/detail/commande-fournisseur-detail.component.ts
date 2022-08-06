import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeFournisseur } from '../commande-fournisseur.model';

@Component({
  selector: 'jhi-commande-fournisseur-detail',
  templateUrl: './commande-fournisseur-detail.component.html',
})
export class CommandeFournisseurDetailComponent implements OnInit {
  commandeFournisseur: ICommandeFournisseur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeFournisseur }) => {
      this.commandeFournisseur = commandeFournisseur;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
