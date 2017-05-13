import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import { CalendarEventAction, CalendarEvent } from 'angular-calendar';
import { addHours, addDays, subDays, isSameMonth, isSameDay, startOfMonth, endOfMonth, startOfWeek, endOfWeek, startOfDay, endOfDay, format } from 'date-fns';
import { Observable } from 'rxjs/Observable';
import {colors} from "./calendar-utils/colors";

@Component({
    selector: 'jhi-calendar-component',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './calendar.component.html'
})
export class CalendarComponent implements OnInit {

    serverUrl: string = '/api/calendars';

    view: string = 'month';

    viewDate: Date = new Date();

    events$: Observable<CalendarEvent[]>;

    activeDayIsOpen: boolean = false;

    events: CalendarEvent[] = [{
        start: subDays(startOfDay(new Date()), 1),
        end: addDays(new Date(), 1),
        title: 'A 3 day event',
        color: colors.red
    }];
/*
    [{
        "id" : 1051,
        "start" : "2017-05-14T01:54:08.218+02:00",
        "end" : "2017-05-14T00:54:08.218+02:00",
        "title" : "Mach halt irgendwas rein",
        "location" : {
            "id" : 1001,
            "streetAddress" : "string",
            "postalCode" : "string",
            "city" : "da",
            "stateProvince" : "string",
            "latitude" : "string",
            "longitude" : "string"
        }
    }]*/

    modalData: {
        action: string,
        event: CalendarEvent
    };


    constructor(private http: Http) {
    }

    ngOnInit(): void {
        this.fetchEvents();
    }

    fetchEvents(): void {

        const getStart: any = {
            month: startOfMonth,
            week: startOfWeek,
            day: startOfDay
        }[this.view];

        const getEnd: any = {
            month: endOfMonth,
            week: endOfWeek,
            day: endOfDay
        }[this.view];

        const search: URLSearchParams = new URLSearchParams();
        search.set('primary_release_date.gte', format(getStart(this.viewDate), 'YYYY-MM-DD'));
        search.set('primary_release_date.lte', format(getEnd(this.viewDate), 'YYYY-MM-DD'));

        this.events$ = this.http
            .get(this.serverUrl)
            .map(res => res.json())
            .map((eventlist: CalendarEvent[]) => {
                return eventlist.map((event: CalendarEvent) => {
                    return {
                        title: event.title,
                        start: new Date(event.start),
                        end: new Date(event.end),
                        color: colors.blue
                    };
                });
            });
    }

    dayClicked({date, events}: { date: Date, events: CalendarEvent[] }): void {
        console.log("dayClicked:date", date);
        console.log("dayClicked:events", events);

        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
                this.viewDate = date;
            }
        }
    }

    eventClicked(event: CalendarEvent): void {
        console.log("eventClicked", event);
    }

}
