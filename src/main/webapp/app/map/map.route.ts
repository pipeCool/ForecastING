import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { MapComponent } from './';

export const MAP_ROUTE: Route = {
    path: 'gmap',
    component: MapComponent,
    data: {
        authorities: [],
        pageTitle: 'map.title'
    }
};
