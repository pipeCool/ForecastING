import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhipsterSampleApplicationNg2SharedModule } from '../shared';
import { STATIC_ROUTE, StaticComponent } from './';

@NgModule({
    imports: [
        JhipsterSampleApplicationNg2SharedModule,
        RouterModule.forRoot([ STATIC_ROUTE ], { useHash: true })
    ]
})
export class JhipsterSampleApplicationNg2StaticModule {}
