import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FintechProject1TestModule } from '../../../test.module';
import { QUpdateComponent } from 'app/entities/q/q-update.component';
import { QService } from 'app/entities/q/q.service';
import { Q } from 'app/shared/model/q.model';

describe('Component Tests', () => {
  describe('Q Management Update Component', () => {
    let comp: QUpdateComponent;
    let fixture: ComponentFixture<QUpdateComponent>;
    let service: QService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FintechProject1TestModule],
        declarations: [QUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Q(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Q();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
