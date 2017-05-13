import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Interhyp4HackathonTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransactionMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/transaction/transaction-my-suffix-detail.component';
import { TransactionMySuffixService } from '../../../../../../main/webapp/app/entities/transaction/transaction-my-suffix.service';
import { TransactionMySuffix } from '../../../../../../main/webapp/app/entities/transaction/transaction-my-suffix.model';

describe('Component Tests', () => {

    describe('TransactionMySuffix Management Detail Component', () => {
        let comp: TransactionMySuffixDetailComponent;
        let fixture: ComponentFixture<TransactionMySuffixDetailComponent>;
        let service: TransactionMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Interhyp4HackathonTestModule],
                declarations: [TransactionMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TransactionMySuffixService,
                    EventManager
                ]
            }).overrideComponent(TransactionMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransactionMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransactionMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transaction).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
