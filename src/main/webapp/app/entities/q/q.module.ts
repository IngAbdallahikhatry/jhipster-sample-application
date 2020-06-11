import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FintechProject1SharedModule } from 'app/shared/shared.module';
import { QComponent } from './q.component';
import { QDetailComponent } from './q-detail.component';
import { QUpdateComponent } from './q-update.component';
import { QDeleteDialogComponent } from './q-delete-dialog.component';
import { qRoute } from './q.route';

@NgModule({
  imports: [FintechProject1SharedModule, RouterModule.forChild(qRoute)],
  declarations: [QComponent, QDetailComponent, QUpdateComponent, QDeleteDialogComponent],
  entryComponents: [QDeleteDialogComponent],
})
export class FintechProject1QModule {}
