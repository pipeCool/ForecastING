import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import { CalendarEvent } from 'angular-calendar';
import { addHours, addDays, subDays, isSameMonth, isSameDay, startOfMonth, endOfMonth, startOfWeek, endOfWeek, startOfDay, endOfDay, format } from 'date-fns';
import { Observable } from 'rxjs/Observable';
import { EventColor } from 'angular-calendar';

interface GoogleCalendarEvent extends CalendarEvent {
    backgroundColor: string;
    foregroundColor: string;
}

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

        const timespan: string = "?from=2010-05-13T04:30:38.472Z&till=2020-05-13T04:30:38.472Z";
        let eventIndex: number = 0;

        this.events$ = this.http
            .get(this.serverUrl + timespan)
            .map(res => res.json())
            .map((eventlist: CalendarEvent[]) => {
                return eventlist.map((event: GoogleCalendarEvent) => {
                    let eventColor: EventColor = {
                        primary: event.backgroundColor,
                        secondary: '#fff'
                    };
                    return {
                        title: event.title,
                        start: new Date(event.start),
                        end: new Date(event.end),
                        resizable: {
                            beforeStart: true,
                            afterEnd: true
                        },
                        color: eventColor
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
