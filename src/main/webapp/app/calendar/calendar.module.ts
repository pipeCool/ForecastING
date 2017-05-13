import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'angular-calendar';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { Interhyp4HackathonSharedModule } from '../shared';
import { CALENDAR_ROUTE, CalendarComponent} from './';
import { CalendarUtilsModule } from './calendar-utils/module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbModalModule.forRoot(),
        CalendarModule.forRoot(),
        BrowserAnimationsModule,
        CalendarUtilsModule,
        Interhyp4HackathonSharedModule,
        RouterModule.forRoot([ CALENDAR_ROUTE ], { useHash: true })
    ],
    declarations: [
        CalendarComponent
    ],
    exports: [
        CalendarComponent
    ]
})
export class Interhyp4HackathonEntityModuleCalendarModule {}
