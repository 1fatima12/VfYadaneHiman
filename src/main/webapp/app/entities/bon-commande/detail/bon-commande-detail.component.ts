import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonCommande } from '../bon-commande.model';

@Component({
  selector: 'jhi-bon-commande-detail',
  templateUrl: './bon-commande-detail.component.html',
})
export class BonCommandeDetailComponent implements OnInit {
  bonCommande: IBonCommande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonCommande }) => {
      this.bonCommande = bonCommande;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
