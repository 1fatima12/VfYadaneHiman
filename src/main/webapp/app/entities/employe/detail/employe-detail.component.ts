import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmploye } from '../employe.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-employe-detail',
  templateUrl: './employe-detail.component.html',
})
export class EmployeDetailComponent implements OnInit {
  employe: IEmploye | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.employe = employe;
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
