import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Video } from '../video';
@Injectable({
  providedIn: 'root'
})
export class VideoDLServiceService {

  private videosUrl: string;
  private videoChosenForDL:string = '';

    constructor(private http: HttpClient) {
      //url toward the link to the backend
      // associated to videos database page
      this.videosUrl = 'http://localhost:8080/videos';
    }

  /**
  This method enables to prepare the video name for a new video link for a download
  */
  public ajoutVideoDL(videoName:string){
      this.videoChosenForDL = videoName;
  }

  public getVideoChosenForDL():string{
    return this.videoChosenForDL;
  }

    /**
     retrive all the videos downloaded so far by every users
     */
    public findAll(): Observable<Video[]> {
      return this.http.get<Video[]>(this.videosUrl);
    }

  /** sauvegarde en base une vidéo choisie
  la méthode save réalise une requête avec
  l'@ "'http://localhost:8080/videos'"
  du constructor.
  Elle est liée à "addVideo"  du
  VideoController.java
  @return
  the Observable<video> with its true id (not the false) to retrieve it in the database system.
  */
    public save(video: Video):Observable<Video> {
      console.log("On sauvegarde une nouvelle vidéo : "+video);
      return this.http.post<Video>(this.videosUrl, video);
    }

  /**
   this video does the video preparation on the server part.
   the link in post has to match with the link in the VideoController
   method one wants to call

   notice : you can only retrieve as observable-compatible
   something that is defined as a controller and repository in java
   */
   /*
   deprecated
  public prepareVideo(video: Video): Observable<Video>{
        console.log("On récupère le titre d'une nouvelle vidéo : "+video);
        //replace by get to display video's name
        return this.http.post<Video>(this.videosUrl+"/title",video);
  }*/

  /**
   cette méthode permet la suppresion d'un user.
   Elle est liée à
   removeUser de UserController.java.
   la méthode delete réalise une requête avec l'@ :

  http://localhost:8080/user
   */
    public delete(video: Video) {
      console.log("On supprime une video : "+video);
      console.log("la vidéo a pour id : "+video.id)
      //use the url associated deleteMapping of UserController
      return this.http.delete(this.videosUrl+"/delete/"+video.id);
      //censé marcher : "http://localhost:8080/users/delete/302"
    }
}
