/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IappReclamosTestModule } from '../../../test.module';
import { TipoReclamoMySuffixDeleteDialogComponent } from 'app/entities/tipo-reclamo-my-suffix/tipo-reclamo-my-suffix-delete-dialog.component';
import { TipoReclamoMySuffixService } from 'app/entities/tipo-reclamo-my-suffix/tipo-reclamo-my-suffix.service';

describe('Component Tests', () => {
    describe('TipoReclamoMySuffix Management Delete Component', () => {
        let comp: TipoReclamoMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<TipoReclamoMySuffixDeleteDialogComponent>;
        let service: TipoReclamoMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TipoReclamoMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(TipoReclamoMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoReclamoMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoReclamoMySuffixService);
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
