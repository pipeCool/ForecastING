import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Interhyp4HackathonTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LocationMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/location/location-my-suffix-detail.component';
import { LocationMySuffixService } from '../../../../../../main/webapp/app/entities/location/location-my-suffix.service';
import { LocationMySuffix } from '../../../../../../main/webapp/app/entities/location/location-my-suffix.model';

describe('Component Tests', () => {

    describe('LocationMySuffix Management Detail Component', () => {
        let comp: LocationMySuffixDetailComponent;
        let fixture: ComponentFixture<LocationMySuffixDetailComponent>;
        let service: LocationMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Interhyp4HackathonTestModule],
                declarations: [LocationMySuffixDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LocationMySuffixService,
                    EventManager
                ]
            }).overrideComponent(LocationMySuffixDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationMySuffixService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LocationMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.location).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
