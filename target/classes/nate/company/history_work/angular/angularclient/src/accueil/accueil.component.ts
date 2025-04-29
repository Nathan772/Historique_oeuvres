import { Component } from '@angular/core';

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrl: './accueil.component.scss'
})
export class AccueilComponent {
  title: string;
  isConnected:Boolean;


  constructor() {
    //this.isConnected = false;
    this.title = "Musée d'oeuvres artistiques";
    this.isConnected = false;
  }
}
