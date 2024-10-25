/* fichier créé et remplit à la main + via baeldung */
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from '../user/user_list/user-list.component';
import { UserFormComponent } from '../user/user_form/user-form.component';
import { UserService } from '../user/user_service/user-service.service';
import {AppComponent } from './app.component';
import {UserEntranceComponent } from '../user/user_entrance/user-entrance.component';
import { UserConnectionComponent } from '../user/user_connection/user-connection.component';
import { MovieSearchComponent } from '../movies/movie_search/movie-search.component';
/*
ce tableau indique quel composant afficher selon
le chemin web "path" choisit (ce sera l'url qui va s'afficher dans la page web et aussi celui qui peut être réutilisé
  dans des appels de fonction)par le user
*/


const routes: Routes = [
  { path: 'entry', component: AppComponent },
  //admin pdv : all th user
  { path: 'users', component: UserListComponent },
  //register path
  { path: 'addUser', component: UserFormComponent },
  //path for searching user
  { path: 'userSearch', component: UserFormComponent },
  //removal path
  { path: 'users/delete', component: UserListComponent },
  //welcome page for connected user
  {path: 'user/entrance' , component: UserEntranceComponent},
  //connection page
  {path: 'connection' , component: UserConnectionComponent},

  //connection page
  //Update avec la page pour la liste de films du user...
  {path: 'user/entrance/user/myListMovies' , component: MovieSearchComponent},


  //{path: 'user/myListMangas' , component: UserListMangasComponent},
  //connection path (update later)
  //{ path: 'connectUser', component: UserConnectionComponent },
/*

utiliser les router pour associer
des pages associés à des comptes users
en précisant le numéro user dans le path :
users/{id}/videos

afficherait les vidéos propres au user,
etc...

*/
  /*
  deprecated
  /*
  { path: 'videos', component: VideoListComponent },
  { path: 'videos/delete', component: VideoListComponent },
  { path: 'videos/downloadPage', component: VideoDLPageComponent },
  { path: 'videos/form', component: VideoDLFormComponent }*/


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
