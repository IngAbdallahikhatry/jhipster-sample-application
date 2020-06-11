import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FintechProject1SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [FintechProject1SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class FintechProject1HomeModule {}
