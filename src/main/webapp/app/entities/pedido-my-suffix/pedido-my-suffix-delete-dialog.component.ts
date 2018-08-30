import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffixService } from './pedido-my-suffix.service';

@Component({
    selector: 'jhi-pedido-my-suffix-delete-dialog',
    templateUrl: './pedido-my-suffix-delete-dialog.component.html'
})
export class PedidoMySuffixDeleteDialogComponent {
    pedido: IPedidoMySuffix;

    constructor(private pedidoService: PedidoMySuffixService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pedidoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pedidoListModification',
                content: 'Deleted an pedido'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pedido-my-suffix-delete-popup',
    template: ''
})
export class PedidoMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pedido }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PedidoMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pedido = pedido;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
