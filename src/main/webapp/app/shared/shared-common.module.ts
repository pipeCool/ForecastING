import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from 'ng2-translate';
import { AlertService } from 'ng-jhipster';
import {
    Interhyp4HackathonSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

export function alertServiceProvider(sanitizer: Sanitizer, translateService: TranslateService) {
    // set below to true to make alerts look like toast
    const isToast = false;
    return new AlertService(sanitizer, isToast, translateService);
}

@NgModule({
    imports: [
        Interhyp4HackathonSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer, TranslateService]
        },
        Title
    ],
    exports: [
        Interhyp4HackathonSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class Interhyp4HackathonSharedCommonModule {}
