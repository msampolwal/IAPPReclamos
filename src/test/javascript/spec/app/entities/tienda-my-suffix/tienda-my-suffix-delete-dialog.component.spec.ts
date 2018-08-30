/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IappReclamosTestModule } from '../../../test.module';
import { TiendaMySuffixDeleteDialogComponent } from 'app/entities/tienda-my-suffix/tienda-my-suffix-delete-dialog.component';
import { TiendaMySuffixService } from 'app/entities/tienda-my-suffix/tienda-my-suffix.service';

describe('Component Tests', () => {
    describe('TiendaMySuffix Management Delete Component', () => {
        let comp: TiendaMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<TiendaMySuffixDeleteDialogComponent>;
        let service: TiendaMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TiendaMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(TiendaMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TiendaMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TiendaMySuffixService);
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
