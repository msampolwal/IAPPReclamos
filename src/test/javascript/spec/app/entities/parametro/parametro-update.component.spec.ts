/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { ParametroUpdateComponent } from 'app/entities/parametro/parametro-update.component';
import { ParametroService } from 'app/entities/parametro/parametro.service';
import { Parametro } from 'app/shared/model/parametro.model';

describe('Component Tests', () => {
    describe('Parametro Management Update Component', () => {
        let comp: ParametroUpdateComponent;
        let fixture: ComponentFixture<ParametroUpdateComponent>;
        let service: ParametroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [ParametroUpdateComponent]
            })
                .overrideTemplate(ParametroUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParametroUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametroService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Parametro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parametro = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Parametro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parametro = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
