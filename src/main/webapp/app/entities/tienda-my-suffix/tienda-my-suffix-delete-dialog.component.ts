import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';
import { TiendaMySuffixService } from './tienda-my-suffix.service';

@Component({
    selector: 'jhi-tienda-my-suffix-delete-dialog',
    templateUrl: './tienda-my-suffix-delete-dialog.component.html'
})
export class TiendaMySuffixDeleteDialogComponent {
    tienda: ITiendaMySuffix;

    constructor(private tiendaService: TiendaMySuffixService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tiendaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tiendaListModification',
                content: 'Deleted an tienda'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tienda-my-suffix-delete-popup',
    template: ''
})
export class TiendaMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tienda }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TiendaMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tienda = tienda;
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
