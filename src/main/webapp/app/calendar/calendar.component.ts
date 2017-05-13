import {Component, ChangeDetectionStrategy, OnInit} from '@angular/core';
import {Http, URLSearchParams} from '@angular/http';
import 'rxjs/add/operator/map';
import {CalendarEvent} from 'angular-calendar';
import {
    isSameMonth,
    isSameDay,
    startOfMonth,
    endOfMonth,
    startOfWeek,
    endOfWeek,
    startOfDay,
    endOfDay,
    format
} from 'date-fns';
import {Observable} from 'rxjs/Observable';

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

    constructor(private http: Http) {
    }

    ngOnInit(): void {
        this.fetchEvents();
    }

    fetchEvents(): void {
        this.events$ = this.http
            .get(this.serverUrl)
            .map((res) => {
                console.log(res.json());
                return res.json();
            });
    }

    dayClicked({date, events}: { date: Date, events: CalendarEvent[] }): void {
        alert('dayClicked');

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
        alert('eventClicked');
    }

}
