import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffixService } from './reclamo-my-suffix.service';

@Component({
    selector: 'jhi-reclamo-my-suffix-delete-dialog',
    templateUrl: './reclamo-my-suffix-delete-dialog.component.html'
})
export class ReclamoMySuffixDeleteDialogComponent {
    reclamo: IReclamoMySuffix;

    constructor(
        private reclamoService: ReclamoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reclamoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reclamoListModification',
                content: 'Deleted an reclamo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reclamo-my-suffix-delete-popup',
    template: ''
})
export class ReclamoMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reclamo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReclamoMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reclamo = reclamo;
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
