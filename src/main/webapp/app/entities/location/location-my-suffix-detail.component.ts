import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { LocationMySuffix } from './location-my-suffix.model';
import { LocationMySuffixService } from './location-my-suffix.service';

@Component({
    selector: 'jhi-location-my-suffix-detail',
    templateUrl: './location-my-suffix-detail.component.html'
})
export class LocationMySuffixDetailComponent implements OnInit, OnDestroy {

    location: LocationMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private locationService: LocationMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocations();
    }

    load(id) {
        this.locationService.find(id).subscribe((location) => {
            this.location = location;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'locationListModification',
            (response) => this.load(this.location.id)
        );
    }
}
