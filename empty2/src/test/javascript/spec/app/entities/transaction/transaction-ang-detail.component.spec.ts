import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Empty2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransactionAngDetailComponent } from '../../../../../../main/webapp/app/entities/transaction/transaction-ang-detail.component';
import { TransactionAngService } from '../../../../../../main/webapp/app/entities/transaction/transaction-ang.service';
import { TransactionAng } from '../../../../../../main/webapp/app/entities/transaction/transaction-ang.model';

describe('Component Tests', () => {

    describe('TransactionAng Management Detail Component', () => {
        let comp: TransactionAngDetailComponent;
        let fixture: ComponentFixture<TransactionAngDetailComponent>;
        let service: TransactionAngService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Empty2TestModule],
                declarations: [TransactionAngDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TransactionAngService,
                    EventManager
                ]
            }).overrideComponent(TransactionAngDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionAngDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionAngService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransactionAng(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transaction).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
