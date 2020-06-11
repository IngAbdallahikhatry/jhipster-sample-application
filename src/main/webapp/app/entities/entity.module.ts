import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.FintechProject1RegionModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.FintechProject1CountryModule),
      },
      {
        path: 'q',
        loadChildren: () => import('./q/q.module').then(m => m.FintechProject1QModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.FintechProject1LocationModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.FintechProject1DepartmentModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.FintechProject1TaskModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.FintechProject1EmployeeModule),
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.FintechProject1JobModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.FintechProject1JobHistoryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FintechProject1EntityModule {}
