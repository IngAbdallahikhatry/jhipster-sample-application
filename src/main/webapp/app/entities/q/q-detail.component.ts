import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQ } from 'app/shared/model/q.model';

@Component({
  selector: 'jhi-q-detail',
  templateUrl: './q-detail.component.html',
})
export class QDetailComponent implements OnInit {
  q: IQ | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ q }) => (this.q = q));
  }

  previousState(): void {
    window.history.back();
  }
}
