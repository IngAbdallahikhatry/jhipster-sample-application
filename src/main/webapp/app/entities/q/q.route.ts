import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQ, Q } from 'app/shared/model/q.model';
import { QService } from './q.service';
import { QComponent } from './q.component';
import { QDetailComponent } from './q-detail.component';
import { QUpdateComponent } from './q-update.component';

@Injectable({ providedIn: 'root' })
export class QResolve implements Resolve<IQ> {
  constructor(private service: QService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQ> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((q: HttpResponse<Q>) => {
          if (q.body) {
            return of(q.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Q());
  }
}

export const qRoute: Routes = [
  {
    path: '',
    component: QComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fintechProject1App.q.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QDetailComponent,
    resolve: {
      q: QResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fintechProject1App.q.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QUpdateComponent,
    resolve: {
      q: QResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fintechProject1App.q.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QUpdateComponent,
    resolve: {
      q: QResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fintechProject1App.q.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
