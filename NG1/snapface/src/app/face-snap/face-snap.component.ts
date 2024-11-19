import { Component, Input, OnInit } from '@angular/core';
import { FaceSnap } from '../models/face-snap';

@Component({
  selector: 'app-face-snap',
  standalone: true,
  imports: [],
  templateUrl: './face-snap.component.html',
  styleUrl: './face-snap.component.scss'
})
export class FaceSnapComponent implements OnInit {
  @Input() faceSnap!: FaceSnap;

  snapButtonText!: string;
  userHasSnapped!: Boolean;

ngOnInit() {
  
  this.userHasSnapped = false;
  this.snapButtonText = 'Oh snap !';
}

onSnap(): void {
  if (this.userHasSnapped) {
    this.unSnap();
  } else {
    this.snap();
  }
}

unSnap() {
  this.faceSnap.removeSnap();
  this.snapButtonText = 'Oh Snap!';
  this.userHasSnapped = false;
}

snap() {
  this.faceSnap.addSnap();
  this.snapButtonText = 'Oops, unSnap!';
  this.userHasSnapped = true;
}

}