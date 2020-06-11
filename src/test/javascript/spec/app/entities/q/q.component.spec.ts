import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FintechProject1TestModule } from '../../../test.module';
import { QComponent } from 'app/entities/q/q.component';
import { QService } from 'app/entities/q/q.service';
import { Q } from 'app/shared/model/q.model';

describe('Component Tests', () => {
  describe('Q Management Component', () => {
    let comp: QComponent;
    let fixture: ComponentFixture<QComponent>;
    let service: QService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FintechProject1TestModule],
        declarations: [QComponent],
      })
        .overrideTemplate(QComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Q(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.qs && comp.qs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
