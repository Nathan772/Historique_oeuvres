/* fichier créé et remplit à la main + via baeldung */
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from '../user/user_list/user-list.component';
import { UserFormComponent } from '../user/user_form/user-form.component';
import { UserService } from '../user/user_service/user-service.service';
import { VideoDLServiceService } from '../videoDL/videoDLService/video-dlservice.service';
import { VideoDLFormComponent } from '../videoDL/videoDLForm/video-dl-form.component';
import { VideoListComponent } from '../videoDL/videoList/video-list.component';
import { VideoDLPageComponent } from '../videoDL/videoDLPage/video-dlpage.component';
import {AppComponent } from './app.component';
/*
ce tableau indique quel composant afficher selon
le chemin web "path" choisit par le user
comment les noms sont
définis ??
ils sont associés à
des choses existantes normalement,
adduser semble être le nom de la fonction d'ajout
utilisé dans userController
"path:users" est réutilisée
 dans le fichier
 user-form pour naviguer entre les pages après l'ajout d'un utilisateur
*/
/*

utiliser les router pour associer
des pages associés à des comptes users
en précisant le numéro user dans le path :
users/{id}/videos

afficherait les vidéos propres au user,
etc...

*/
const routes: Routes = [
  { path: 'entry', component: AppComponent },
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },

  { path: 'users/delete', component: UserListComponent },
  { path: 'videos', component: VideoListComponent },
  { path: 'videos/delete', component: VideoListComponent },
  { path: 'videos/downloadPage', component: VideoDLPageComponent },
  { path: 'videos/form', component: VideoDLFormComponent }

];

/*
@Component({
  selector:'app-routing-user'
  })*/
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
/*
@Injectable({
  providedIn:'root'})*/
export class AppRoutingModule { }
