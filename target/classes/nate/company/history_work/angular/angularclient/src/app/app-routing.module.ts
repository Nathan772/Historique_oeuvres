/* fichier créé et remplit à la main + via baeldung */
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
//import { UserListComponent } from '../user/user_list/user-list.component';




import { UserFormComponent } from '../user/user_form/user-form.component';
import { UserConnectionComponent } from '../user/user_connection/user-connection.component';


import { UserService } from '../user/user_service/user-service.service';
import {AppComponent } from './app.component';

import { MovieSearchComponent } from '../movies/movie_search/movie-search.component';
import { AccueilComponent } from '../accueil/accueil.component';

import {UserListComponent} from '../user/user_list/user-list.component';
import { UserMovieListComponent } from '../user/user_movie_list/user-movie-list.component';
import {UserEntranceComponent } from '../user/user_entrance/user-entrance.component';
import { MovieMenuUserComponent } from '../user/movie_menu_user/movie-menu-user.component';

/*
ce tableau indique quel composant afficher selon
le chemin web "path" choisit (ce sera l'url qui va s'afficher dans la page web et aussi celui qui peut être réutilisé
  dans des appels de fonction)par le user
*/


const routes: Routes = [
  { path: '', component: AccueilComponent},

  { path: 'entry', component: AppComponent },
  //admin pdv : all th user
  //{ path: 'users', component: UserListComponent },
  //register path
  { path: 'addUser', component: UserFormComponent },
  //path for searching user
  { path: 'userSearch', component: UserFormComponent },
  //removal path
  //{ path: 'users/delete', component: UserListComponent },
  //welcome page for connected user
  {path: 'user/entrance' , component: UserEntranceComponent},
  //connection page
  {path: 'connection' , component: UserConnectionComponent},

  //page choice : watch movie list or add movie to the list
  {path: 'user/entrance/menuMovieChoice' , component: MovieMenuUserComponent},

   //search movie page
  {path: 'user/entrance/menuMovieChoice/SearchMovie' , component: MovieSearchComponent},

   //user's movie ist
   {path: 'user/entrance/menuMovieChoice/listMovies' , component: UserMovieListComponent},

   {path: 'user/entrance/listUsers' , component: UserListComponent},

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
