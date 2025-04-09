import { Component, OnInit } from '@angular/core';
import { Video } from '../video'
import { ActivatedRoute, Router } from '@angular/router';
import { VideoDLServiceService } from '../videoDLService/video-dlservice.service';
@Component({
  selector: 'app-video-dlpage',
  templateUrl: './video-dlpage.component.html',
  styleUrl: './video-dlpage.component.scss'
})
export class VideoDLPageComponent implements OnInit {

/*
next problem :
try to retrieve the name of the file emit, in the proper way

*/
  videoService:VideoDLServiceService;
  /*
  need to create a method in order to retrive this path in a proper way, not by
  writing directly the name of the method.
  */
  videoFolder:string = "/home/nathanb/Bureau/Bureau/Bureau/Perso/projets_développement_informatique/VideoConverterPersonalFiles/MP3_output";
  router:Router;

  constructor(routerParam: Router, service: VideoDLServiceService) {
      this.videoService = service;
      this.router = routerParam;
  }

  //utiliser les router pour associer
  //des pages associés à des comptes users
  gotoVideoPage(){
      this.router.navigate(['/videos']);
  }

  ngOnInit(){}
}
