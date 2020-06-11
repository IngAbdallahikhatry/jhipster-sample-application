import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQ, Q } from 'app/shared/model/q.model';
import { QService } from './q.service';

@Component({
  selector: 'jhi-q-update',
  templateUrl: './q-update.component.html',
})
export class QUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    countryName: [],
  });

  constructor(protected qService: QService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ q }) => {
      this.updateForm(q);
    });
  }

  updateForm(q: IQ): void {
    this.editForm.patchValue({
      id: q.id,
      countryName: q.countryName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const q = this.createFromForm();
    if (q.id !== undefined) {
      this.subscribeToSaveResponse(this.qService.update(q));
    } else {
      this.subscribeToSaveResponse(this.qService.create(q));
    }
  }

  private createFromForm(): IQ {
    return {
      ...new Q(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQ>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
