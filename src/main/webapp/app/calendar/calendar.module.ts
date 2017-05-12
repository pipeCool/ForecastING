/**
 * Created by Notebook-9 on 12/05/2017.
 */

import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'angular-calendar';

@NgModule({
    imports: [
        BrowserAnimationsModule, // angular 4.0+ only
        CalendarModule.forRoot()
    ]
})
export class MyModule {}
