import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AgmCoreModule } from 'angular2-google-maps/core';
import { MAP_ROUTE, MapComponent} from './';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    imports: [
        BrowserModule,
        AgmCoreModule.forRoot()
    ],
    declarations: [
        MapComponent
    ],
    exports: [
        MapComponent
    ]
})
export class Interhyp4HackathonMapModule {}
