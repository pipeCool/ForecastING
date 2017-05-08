import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Empty2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BankAccountAngDetailComponent } from '../../../../../../main/webapp/app/entities/bank-account/bank-account-ang-detail.component';
import { BankAccountAngService } from '../../../../../../main/webapp/app/entities/bank-account/bank-account-ang.service';
import { BankAccountAng } from '../../../../../../main/webapp/app/entities/bank-account/bank-account-ang.model';

describe('Component Tests', () => {

    describe('BankAccountAng Management Detail Component', () => {
        let comp: BankAccountAngDetailComponent;
        let fixture: ComponentFixture<BankAccountAngDetailComponent>;
        let service: BankAccountAngService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Empty2TestModule],
                declarations: [BankAccountAngDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BankAccountAngService,
                    EventManager
                ]
            }).overrideComponent(BankAccountAngDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BankAccountAngDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankAccountAngService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BankAccountAng(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bankAccount).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
