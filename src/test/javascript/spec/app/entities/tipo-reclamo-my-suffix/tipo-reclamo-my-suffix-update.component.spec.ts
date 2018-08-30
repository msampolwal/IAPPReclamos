/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { TipoReclamoMySuffixUpdateComponent } from 'app/entities/tipo-reclamo-my-suffix/tipo-reclamo-my-suffix-update.component';
import { TipoReclamoMySuffixService } from 'app/entities/tipo-reclamo-my-suffix/tipo-reclamo-my-suffix.service';
import { TipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';

describe('Component Tests', () => {
    describe('TipoReclamoMySuffix Management Update Component', () => {
        let comp: TipoReclamoMySuffixUpdateComponent;
        let fixture: ComponentFixture<TipoReclamoMySuffixUpdateComponent>;
        let service: TipoReclamoMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TipoReclamoMySuffixUpdateComponent]
            })
                .overrideTemplate(TipoReclamoMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoReclamoMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoReclamoMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoReclamoMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoReclamo = entity;
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
                    const entity = new TipoReclamoMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoReclamo = entity;
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
