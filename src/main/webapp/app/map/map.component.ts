import { Component } from '@angular/core';

// just an interface for type safety.
interface marker {
    lat: number;
    lng: number;
    label?: string;
    draggable: boolean;
}

@Component({
    selector: 'my-app',
    styles: [`
    .sebm-google-map-container {
       height: 300px;
     }
  `],
    template: `
    <sebm-google-map 
      [latitude]="lat"
      [longitude]="lng"
      [zoom]="zoom"
      [disableDefaultUI]="false"
      [zoomControl]="false"
      (mapClick)="mapClicked($event)">
    
      <sebm-google-map-marker 
          *ngFor="let m of markers; let i = index"
          (markerClick)="clickedMarker(m.label, i)"
          [latitude]="m.lat"
          [longitude]="m.lng"
          [label]="m.label"
          [markerDraggable]="m.draggable"
          (dragEnd)="markerDragEnd(m, $event)">
          
        <sebm-google-map-info-window>
          <strong>InfoWindow content</strong>
        </sebm-google-map-info-window>
        
      </sebm-google-map-marker>
      
      <sebm-google-map-circle [latitude]="lat" [longitude]="lng" 
          [radius]="2500"
          [fillColor]="'orange'"
          [circleDraggable]="true"
          [editable]="true">
      </sebm-google-map-circle>

    </sebm-google-map>
`})

export class MapComponent {
    // google maps zoom level
    zoom: number = 12;

    // initial center position for the map
    lat: number = 50.26304;
    lng: number = 19.0797127;

    clickedMarker(label: string, index: number) {
        console.log(`clicked the marker: ${label || index}`)
    }

    markerDragEnd(m: marker, $event: MouseEvent) {
        console.log('dragEnd', m, $event);
    }

    markers: marker[] = [
        {
            lat: 50.2616144,
            lng: 19.0544295,
            label: '50',
            draggable: false
        },
        {
            lat: 50.2452397,
            lng: 19.0781187,
            label: '75',
            draggable: false
        }
    ]
}
