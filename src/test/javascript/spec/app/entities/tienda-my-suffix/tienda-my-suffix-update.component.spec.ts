/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { TiendaMySuffixUpdateComponent } from 'app/entities/tienda-my-suffix/tienda-my-suffix-update.component';
import { TiendaMySuffixService } from 'app/entities/tienda-my-suffix/tienda-my-suffix.service';
import { TiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';

describe('Component Tests', () => {
    describe('TiendaMySuffix Management Update Component', () => {
        let comp: TiendaMySuffixUpdateComponent;
        let fixture: ComponentFixture<TiendaMySuffixUpdateComponent>;
        let service: TiendaMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TiendaMySuffixUpdateComponent]
            })
                .overrideTemplate(TiendaMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TiendaMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TiendaMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TiendaMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tienda = entity;
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
                    const entity = new TiendaMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tienda = entity;
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
