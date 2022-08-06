import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMagazin } from '../magazin.model';

@Component({
  selector: 'jhi-magazin-detail',
  templateUrl: './magazin-detail.component.html',
})
export class MagazinDetailComponent implements OnInit {
  magazin: IMagazin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ magazin }) => {
      this.magazin = magazin;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
