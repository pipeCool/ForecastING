import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { Interhyp4HackathonSharedModule, UserRouteAccessService } from './shared';
import { Interhyp4HackathonHomeModule } from './home/home.module';
import { Interhyp4HackathonAdminModule } from './admin/admin.module';
import { Interhyp4HackathonAccountModule } from './account/account.module';
import { Interhyp4HackathonEntityModule } from './entities/entity.module';
import { Interhyp4HackathonCalendarModule } from './calendar/calendar.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        Interhyp4HackathonSharedModule,
        Interhyp4HackathonHomeModule,
        Interhyp4HackathonAdminModule,
        Interhyp4HackathonAccountModule,
        Interhyp4HackathonEntityModule,
        Interhyp4HackathonCalendarModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class Interhyp4HackathonAppModule {}
