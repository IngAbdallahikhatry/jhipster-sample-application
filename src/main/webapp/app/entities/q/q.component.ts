import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQ } from 'app/shared/model/q.model';
import { QService } from './q.service';
import { QDeleteDialogComponent } from './q-delete-dialog.component';

@Component({
  selector: 'jhi-q',
  templateUrl: './q.component.html',
})
export class QComponent implements OnInit, OnDestroy {
  qs?: IQ[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected qService: QService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.qService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IQ[]>) => (this.qs = res.body || []));
      return;
    }

    this.qService.query().subscribe((res: HttpResponse<IQ[]>) => (this.qs = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQ): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQS(): void {
    this.eventSubscriber = this.eventManager.subscribe('qListModification', () => this.loadAll());
  }

  delete(q: IQ): void {
    const modalRef = this.modalService.open(QDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.q = q;
  }
}
