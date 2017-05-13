import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AgmCoreModule } from 'angular2-google-maps/core';
import { RouterModule } from '@angular/router';
import { MAP_ROUTE, MapComponent} from './';

@NgModule({
    imports: [
        BrowserModule,
        RouterModule.forRoot([ MAP_ROUTE ], { useHash: true }),
        AgmCoreModule.forRoot()
    ],
    declarations: [ MapComponent ],
    bootstrap: [ MapComponent ]
})
export class Interhyp4HackathonMapModule {}
