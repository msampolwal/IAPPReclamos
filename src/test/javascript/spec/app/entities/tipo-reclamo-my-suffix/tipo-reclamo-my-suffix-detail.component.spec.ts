/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { TipoReclamoMySuffixDetailComponent } from 'app/entities/tipo-reclamo-my-suffix/tipo-reclamo-my-suffix-detail.component';
import { TipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';

describe('Component Tests', () => {
    describe('TipoReclamoMySuffix Management Detail Component', () => {
        let comp: TipoReclamoMySuffixDetailComponent;
        let fixture: ComponentFixture<TipoReclamoMySuffixDetailComponent>;
        const route = ({ data: of({ tipoReclamo: new TipoReclamoMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TipoReclamoMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoReclamoMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoReclamoMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoReclamo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
