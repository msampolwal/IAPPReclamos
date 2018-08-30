/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { ReclamoMySuffixUpdateComponent } from 'app/entities/reclamo-my-suffix/reclamo-my-suffix-update.component';
import { ReclamoMySuffixService } from 'app/entities/reclamo-my-suffix/reclamo-my-suffix.service';
import { ReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';

describe('Component Tests', () => {
    describe('ReclamoMySuffix Management Update Component', () => {
        let comp: ReclamoMySuffixUpdateComponent;
        let fixture: ComponentFixture<ReclamoMySuffixUpdateComponent>;
        let service: ReclamoMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [ReclamoMySuffixUpdateComponent]
            })
                .overrideTemplate(ReclamoMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReclamoMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReclamoMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReclamoMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reclamo = entity;
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
                    const entity = new ReclamoMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reclamo = entity;
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
