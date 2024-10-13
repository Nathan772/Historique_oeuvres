/*
généré manuellement + baeldung
+ moi
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app/app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { UserListComponent } from './user/user_list/user-list.component';
import { UserFormComponent } from './user/user_form/user-form.component';
import { UserService } from './user/user_service/user-service.service';
import {RouterModule} from '@angular/router';
import { VideoDLServiceService } from './videoDL/videoDLService/video-dlservice.service';
import {VideoDLFormComponent } from './videoDL/videoDLForm/video-dl-form.component';
import {VideoListComponent } from './videoDL/videoList/video-list.component';
import {VideoDLPageComponent } from './videoDL/videoDLPage/video-dlpage.component';

@NgModule({
   declarations: [
      AppComponent,
      UserListComponent,
      UserFormComponent,
      VideoListComponent,
      VideoDLFormComponent,
      VideoDLPageComponent
    ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      RouterModule
    ],
  providers: [UserService, VideoDLServiceService],
  bootstrap:[AppComponent],
})
export class AppModule { }
