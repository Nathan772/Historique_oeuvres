import { Component, OnInit } from '@angular/core';
import {Video } from '../video'
import { ActivatedRoute, Router } from '@angular/router';
import { VideoDLServiceService } from '../videoDLService/video-dlservice.service';

@Component({
  selector: 'app-video-list',
  templateUrl: './video-list.component.html',
  styleUrl: './video-list.component.scss'
})
export class VideoListComponent implements OnInit {

  videos:Video[];
  videoService:VideoDLServiceService;
  router:Router;

  constructor(routerParam: Router, service: VideoDLServiceService) {
    this.videos = [];
    this.videoService = service;
    this.router = routerParam;

  }

/*

this method removes a video
based on their information.
*/

  removeVideo(video:Video){
    //this.userService.delete(user);
    console.log("on supprime la vidéo : "+video);
    /*le subscribe semble obligatoire
    pour permettre le bon déroulement de la suppression
    */
    this.videoService.delete(video).subscribe(data => {
      console.log(video+" a été supprimé ! ");
      })

    //recharge la page après suppression (useless ??)
    window.location.reload();
  }

  gotoVideoList(){
    this.router.navigate(['/videos']);
  }

/*
init the list that contains all the vidéos link
in the database.
*/
  ngOnInit() {
    //stocke les vidéos récupérées
    // dans this.videos
    this.videoService.findAll().subscribe(data => {
              this.videos = data;
            });

  }
}
