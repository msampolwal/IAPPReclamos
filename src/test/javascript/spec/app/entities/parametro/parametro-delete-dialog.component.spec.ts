/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IappReclamosTestModule } from '../../../test.module';
import { ParametroDeleteDialogComponent } from 'app/entities/parametro/parametro-delete-dialog.component';
import { ParametroService } from 'app/entities/parametro/parametro.service';

describe('Component Tests', () => {
    describe('Parametro Management Delete Component', () => {
        let comp: ParametroDeleteDialogComponent;
        let fixture: ComponentFixture<ParametroDeleteDialogComponent>;
        let service: ParametroService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [ParametroDeleteDialogComponent]
            })
                .overrideTemplate(ParametroDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParametroDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametroService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
