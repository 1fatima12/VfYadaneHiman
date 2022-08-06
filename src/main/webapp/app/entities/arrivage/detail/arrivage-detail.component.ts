import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArrivage } from '../arrivage.model';

@Component({
  selector: 'jhi-arrivage-detail',
  templateUrl: './arrivage-detail.component.html',
})
export class ArrivageDetailComponent implements OnInit {
  arrivage: IArrivage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrivage }) => {
      this.arrivage = arrivage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
