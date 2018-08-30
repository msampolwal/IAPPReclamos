/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IappReclamosTestModule } from '../../../test.module';
import { PedidoMySuffixDeleteDialogComponent } from 'app/entities/pedido-my-suffix/pedido-my-suffix-delete-dialog.component';
import { PedidoMySuffixService } from 'app/entities/pedido-my-suffix/pedido-my-suffix.service';

describe('Component Tests', () => {
    describe('PedidoMySuffix Management Delete Component', () => {
        let comp: PedidoMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<PedidoMySuffixDeleteDialogComponent>;
        let service: PedidoMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [PedidoMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(PedidoMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PedidoMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoMySuffixService);
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
