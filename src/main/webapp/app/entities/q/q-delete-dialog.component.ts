import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQ } from 'app/shared/model/q.model';
import { QService } from './q.service';

@Component({
  templateUrl: './q-delete-dialog.component.html',
})
export class QDeleteDialogComponent {
  q?: IQ;

  constructor(protected qService: QService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qListModification');
      this.activeModal.close();
    });
  }
}
