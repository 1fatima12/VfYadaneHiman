import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaiement } from '../paiement.model';

@Component({
  selector: 'jhi-paiement-detail',
  templateUrl: './paiement-detail.component.html',
})
export class PaiementDetailComponent implements OnInit {
  paiement: IPaiement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
