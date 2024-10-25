import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { MainComponent } from './main/main.component';
/*il faut l'importer soi-même*/
import { MovielistComponent } from './movielist/movielist.component';
import { MovieCardComponent } from './movie-card/movie-card.component'

/*il faut l'importer soi-même*/

import { NavbarComponent } from './navbar/navbar.component';

/* il faut le réimporter soit même*/
import { HttpClientModule } from '@angular/common/http';

/* remarque : il est indispensable
de faire l'import de 
httpclientModule manuellemnent sinon cela
créera des bugs*/
/* de même après chaque import au-dessus,
il faut faire manuellement la
déclarations de l'élément dans declarations 
*/
@NgModule({
  imports: [BrowserModule, FormsModule, HttpClientModule],

  declarations: [
    AppComponent,
    TodoListComponent,
    MainComponent,
    NavbarComponent,
    MovielistComponent,
    MovieCardComponent,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
