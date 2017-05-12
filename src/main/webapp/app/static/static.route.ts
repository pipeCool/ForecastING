import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { StaticComponent } from './';

export const STATIC_ROUTE: Route = {
    path: 'static',
    component: StaticComponent,
    data: {
        authorities: [],
        pageTitle: 'static.title'
    }
};
