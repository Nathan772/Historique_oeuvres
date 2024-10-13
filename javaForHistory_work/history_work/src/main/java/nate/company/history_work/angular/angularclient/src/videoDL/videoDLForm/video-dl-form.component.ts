import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Video } from '../video';
import { VideoDLServiceService } from '../videoDLService/video-dlservice.service'
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-video-dl-form',
  templateUrl: './video-dl-form.component.html',
  styleUrl: './video-dl-form.component.scss'
})
export class VideoDLFormComponent implements OnInit {

  router:Router;
  videoService:VideoDLServiceService;
  video:Video;
  //loading video
  videoLoading:boolean=false;

  /*
  emiter that will enable to send the urlLink to the father (app.component)
  each time a new link is prepared.
  It will enable other component to use it.
  */
  @Output()
  serviceEmit = new EventEmitter<VideoDLServiceService>();
  /*
  le user service du constructeur permet d'être utilisé
  pour sauvegarder la vidéo
  */
  constructor(routerParam: Router,service: VideoDLServiceService) {
    this.router = routerParam;
    this.videoService=service;
    /*nécessaire même si sera remplacé
    car cela correspond aux valeurs par défaut de l'utilisateur
    même si l'id pourrait causer un conflit,
    en pratique ce n'est pas le cas
    car il est aussi automatiquement
    remplacé par la base de donnée
    par le suivant du serial !!
    */
    this.video = {title: "", id:"5", link:""}
  }

  /*
  prépare la page de téléchargement + enregistre la vidéo en base
  */
  dlNewVideo(){
    //beginning of loading
    this.videoLoading = true;
    //save the video link + its id (il faudra changer par sauvegarde or update)
    this.videoService.save(this.video).subscribe(actualVideoWithActualData =>
        //this.stayHere()
          /*
          perform the video downloadThrough java method
          and then,
          go to the downloadPage
          */
          {
            //video link recognized
            if(actualVideoWithActualData != null){
                                  /*
                                  retrieve the title of the video to display it to the user later
                                  */
                                  //fonctionnel
                                  this.videoService.ajoutVideoDL(actualVideoWithActualData.title);
                                  /*send the updated service to the app component in order to let
                                      him send the name of the file + the path for download to another component (videoDLPage)
                                      is it necessary to be in waiting + receving mode to receive the data sent by emitter ???
                                    (EMIT seems to work and update the service)
                                    */
                                    this.serviceEmit.emit(this.videoService);
                                    /*
                                    the play link is ready , just go to the downloadPage to enable it
                                    (mayba later could retrieve the path for the download Link)
                                    */
                                    //end of loading
                                    this.videoLoading=false;
                                    this.goToDownloadPage();
            }
            //video link not recognized : reloadpage
            else{
              console.log("video link not recognized")
              //this.router.relo(['videos/form']);
              window.location.reload();
            }

          }

    )
  }

stayHere(){
    this.router.navigate(['/videos/form']);
}

/**
 this method enables to go to the downloadPage for a video
 in order to download it.
 */
goToDownloadPage(){
  this.router.navigate(['/videos/downloadPage']);
}

  /*
  registerNewVideo() {
    //le goto , redirige vers le /users path
    //on appelle le save de user-service
    //pour sauvegarder en base en appelant en fait du java
    this.videoService.save(this.video).subscribe(
      result => this.gotoVideoList());
  }*/

  gotoVideoList() {
      this.router.navigate(['/videos']);
    }

  gotoEntryPage() {
    this.router.navigate(['/entry']);
  }

  ngOnInit(){}

}
