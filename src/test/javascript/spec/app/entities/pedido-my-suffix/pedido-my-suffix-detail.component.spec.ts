/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { PedidoMySuffixDetailComponent } from 'app/entities/pedido-my-suffix/pedido-my-suffix-detail.component';
import { PedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';

describe('Component Tests', () => {
    describe('PedidoMySuffix Management Detail Component', () => {
        let comp: PedidoMySuffixDetailComponent;
        let fixture: ComponentFixture<PedidoMySuffixDetailComponent>;
        const route = ({ data: of({ pedido: new PedidoMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [PedidoMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PedidoMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PedidoMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pedido).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
