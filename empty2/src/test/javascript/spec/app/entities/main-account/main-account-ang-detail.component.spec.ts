import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Empty2TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MainAccountAngDetailComponent } from '../../../../../../main/webapp/app/entities/main-account/main-account-ang-detail.component';
import { MainAccountAngService } from '../../../../../../main/webapp/app/entities/main-account/main-account-ang.service';
import { MainAccountAng } from '../../../../../../main/webapp/app/entities/main-account/main-account-ang.model';

describe('Component Tests', () => {

    describe('MainAccountAng Management Detail Component', () => {
        let comp: MainAccountAngDetailComponent;
        let fixture: ComponentFixture<MainAccountAngDetailComponent>;
        let service: MainAccountAngService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Empty2TestModule],
                declarations: [MainAccountAngDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MainAccountAngService,
                    EventManager
                ]
            }).overrideComponent(MainAccountAngDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MainAccountAngDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MainAccountAngService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MainAccountAng(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mainAccount).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
