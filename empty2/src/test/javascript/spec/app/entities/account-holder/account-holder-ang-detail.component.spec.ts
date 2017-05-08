import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Empty2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AccountHolderAngDetailComponent } from '../../../../../../main/webapp/app/entities/account-holder/account-holder-ang-detail.component';
import { AccountHolderAngService } from '../../../../../../main/webapp/app/entities/account-holder/account-holder-ang.service';
import { AccountHolderAng } from '../../../../../../main/webapp/app/entities/account-holder/account-holder-ang.model';

describe('Component Tests', () => {

    describe('AccountHolderAng Management Detail Component', () => {
        let comp: AccountHolderAngDetailComponent;
        let fixture: ComponentFixture<AccountHolderAngDetailComponent>;
        let service: AccountHolderAngService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Empty2TestModule],
                declarations: [AccountHolderAngDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AccountHolderAngService,
                    EventManager
                ]
            }).overrideComponent(AccountHolderAngDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccountHolderAngDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountHolderAngService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AccountHolderAng(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.accountHolder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
