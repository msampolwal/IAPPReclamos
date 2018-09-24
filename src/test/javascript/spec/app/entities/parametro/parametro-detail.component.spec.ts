/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { ParametroDetailComponent } from 'app/entities/parametro/parametro-detail.component';
import { Parametro } from 'app/shared/model/parametro.model';

describe('Component Tests', () => {
    describe('Parametro Management Detail Component', () => {
        let comp: ParametroDetailComponent;
        let fixture: ComponentFixture<ParametroDetailComponent>;
        const route = ({ data: of({ parametro: new Parametro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [ParametroDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParametroDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParametroDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parametro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
