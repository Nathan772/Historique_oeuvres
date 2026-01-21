
import { Component, Input, OnInit } from '@angular/core';
import { Anime } from '../anime-models/anime-models';
import { AnimeCardComponent } from '../anime-card/anime-card.component';
import { AnimeServiceService } from '../anime_service/anime-service.service';
import { UserService } from '../../user/user_service/user-service.service';

@Component({
  selector: 'app-anime-list',
  templateUrl: './anime-list.component.html',
  styleUrl: './anime-list.component.scss'
})
export class AnimeListComponent {
  /* le mot "Input" est indispensable
    pour permettre au père de communiquer avec
     ce champ pour lui trasmettre des infos
     (voir le html du père)
     */
    @Input()
    listAnimes: Anime[];

    animeService: AnimeServiceService;
    userService : UserService;
    currentPage:number;
    numberPerPage:number;


    constructor(userService:UserService,animeService:AnimeServiceService) {
      /*
      necessary to prevent from 'not init'
      error
      */
      this.listAnimes = [];
      this.animeService = animeService;
      this.userService = userService;
      this.currentPage = 0;
      this.numberPerPage = 3;


    }

    /* c'est dans cette partie que le fils peut
    utiliser les éléments du père*/
    ngOnInit(): void {}

    /**
     this method returns some animes by a number of pages*/
    AnimesByPage() {
      let animesPerPage:Anime[] = []
      for(let i = this.currentPage*this.numberPerPage;i<(this.currentPage+1)*this.numberPerPage;i++){
        animesPerPage.push(this.listAnimes[i])
      }
      return animesPerPage;
    }

   /**

        go to the next page of movies
           */
          increasesPages() {
            if((this.currentPage+1)*this.numberPerPage < this.listAnimes.length){
              this.currentPage+=1;
            }
          }

        /**

        go to the previous page of movies
           */
          decreasesPages() {
            if((this.currentPage-1)*this.numberPerPage >= 0){
              this.currentPage-=1;
            }
          }


    displayAnimes(animes: Anime[]) {}

}
