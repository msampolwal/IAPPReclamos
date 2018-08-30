/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IappReclamosTestModule } from '../../../test.module';
import { ReclamoMySuffixDetailComponent } from 'app/entities/reclamo-my-suffix/reclamo-my-suffix-detail.component';
import { ReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';

describe('Component Tests', () => {
    describe('ReclamoMySuffix Management Detail Component', () => {
        let comp: ReclamoMySuffixDetailComponent;
        let fixture: ComponentFixture<ReclamoMySuffixDetailComponent>;
        const route = ({ data: of({ reclamo: new ReclamoMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IappReclamosTestModule],
                declarations: [ReclamoMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReclamoMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReclamoMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reclamo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
