import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBti } from '../bti.model';

@Component({
  selector: 'jhi-bti-detail',
  templateUrl: './bti-detail.component.html',
})
export class BtiDetailComponent implements OnInit {
  bti: IBti | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bti }) => {
      this.bti = bti;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
