import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FintechProject1TestModule } from '../../../test.module';
import { QDetailComponent } from 'app/entities/q/q-detail.component';
import { Q } from 'app/shared/model/q.model';

describe('Component Tests', () => {
  describe('Q Management Detail Component', () => {
    let comp: QDetailComponent;
    let fixture: ComponentFixture<QDetailComponent>;
    const route = ({ data: of({ q: new Q(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FintechProject1TestModule],
        declarations: [QDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load q on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.q).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
