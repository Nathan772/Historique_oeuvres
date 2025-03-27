import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConnectionServiceService {

  //check if the user isConnected
  public isConnected:Boolean;
  //check if the user already exists after an attempt of creation
  public alreadyExists:Boolean;

  public mismatchedPassword:Boolean;

  constructor() {
    this.alreadyExists = false;
    this.isConnected = false;
    this.mismatchedPassword = false
  }
}
