import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';
import { TipoReclamoMySuffixService } from './tipo-reclamo-my-suffix.service';

@Component({
    selector: 'jhi-tipo-reclamo-my-suffix-delete-dialog',
    templateUrl: './tipo-reclamo-my-suffix-delete-dialog.component.html'
})
export class TipoReclamoMySuffixDeleteDialogComponent {
    tipoReclamo: ITipoReclamoMySuffix;

    constructor(
        private tipoReclamoService: TipoReclamoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoReclamoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoReclamoListModification',
                content: 'Deleted an tipoReclamo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-reclamo-my-suffix-delete-popup',
    template: ''
})
export class TipoReclamoMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoReclamo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoReclamoMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoReclamo = tipoReclamo;
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
