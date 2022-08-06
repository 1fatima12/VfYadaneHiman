import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarque } from '../marque.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-marque-detail',
  templateUrl: './marque-detail.component.html',
})
export class MarqueDetailComponent implements OnInit {
  marque: IMarque | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marque }) => {
      this.marque = marque;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
