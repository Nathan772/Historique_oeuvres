import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user/user_service/user-service.service';

/*

model taken from the page :

https://javascript.plainenglish.io/angular-how-you-can-reload-refresh-a-single-component-or-the-entire-application-and-reuse-the-logic-c6e975a278c3

*/
@Component({
  selector: 'app-common-functionality',
  template:
  `
    <p>
      common-functionality works!
    </p>`,
  styles: [
  ]
})
export class CommonFunctionalityComponent implements OnInit {

  userService:UserService;

  constructor(userService:UserService,public router:Router) {
    this.userService = userService;
    }

  ngOnInit(): void {
  }

  reloadComponent(self:boolean,urlToNavigateTo ?:string){
     //skipLocationChange:true means dont update the url to / when navigating
    console.log("Current route I am on:",this.router.url);
    const url=self ? this.router.url :urlToNavigateTo;
    this.router.navigateByUrl('/',{skipLocationChange:true}).then(()=>{
      this.router.navigate([`/${url}`]).then(()=>{
        console.log(`After navigation I am on:${this.router.url}`)
      })
    })
  }


  reloadPage(){
    /*

    it cause data loss :

    https://stackoverflow.com/questions/62834093/angular-data-loss-when-page-refresh

    */

    //prepare data in order to not loss them during "reload"
    //user data
    localStorage.setItem('userAccountSavedPseudo', this.userService.userAccount.pseudo);
    localStorage.setItem('userAccountSavedPassword', this.userService.userAccount.password);
    localStorage.setItem('userAccountSavedCategory', this.userService.userAccount.category);
    localStorage.setItem('userAccountSavedEmail', this.userService.userAccount.email);
    window.location.reload()
  }

reloadCurrent(){

    this.reloadComponent(true);
  }

}
