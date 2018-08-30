/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { TiendaMySuffixDetailComponent } from 'app/entities/tienda-my-suffix/tienda-my-suffix-detail.component';
import { TiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';

describe('Component Tests', () => {
    describe('TiendaMySuffix Management Detail Component', () => {
        let comp: TiendaMySuffixDetailComponent;
        let fixture: ComponentFixture<TiendaMySuffixDetailComponent>;
        const route = ({ data: of({ tienda: new TiendaMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [TiendaMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TiendaMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TiendaMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tienda).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
