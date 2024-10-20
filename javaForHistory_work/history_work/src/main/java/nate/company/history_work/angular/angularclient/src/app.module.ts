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
import { ConnectionServiceService } from './connection/connection-service.service';
import { UserEntranceComponent} from './user/user_entrance/user-entrance.component';
import { UserConnectionComponent } from './user/user_connection/user-connection.component';
@NgModule({
   declarations: [
      AppComponent,
      UserListComponent,
      UserFormComponent,
      VideoListComponent,
      VideoDLFormComponent,
      VideoDLPageComponent,
      UserEntranceComponent,
      UserConnectionComponent

    ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      RouterModule
    ],
  providers: [UserService, VideoDLServiceService, ConnectionServiceService],
  bootstrap:[AppComponent],
})
export class AppModule { }
