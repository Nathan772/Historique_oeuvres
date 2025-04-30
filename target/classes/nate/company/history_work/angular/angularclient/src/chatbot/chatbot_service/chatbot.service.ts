import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {

  chatbotAIUrl:string;
  constructor(private http: HttpClient, routerParam: Router) {

  //url to send requests to chatbot
    this.chatbotAIUrl = 'http://127.0.0.1:8000/chatbot/';

    }

  /*
  this method send a request to the chatbot and it responds with the answer expected

  */
  public async sendRequestToChatbot(userRequest:string):Promise<string>{
    const headers = new HttpHeaders().append('header', 'value');
        /*
        prepares params source
        https://stackoverflow.com/questions/44280303/angular-http-get-with-parameter
        */
        const params = new HttpParams().append('content', '"content":"'+userRequest+'"')

        const request = "content":"'+userRequest+'"'
        //params = params.append('email', user.email);
       let answer:string = "Error, with my processing, no response... retry later...";
         this.http.get<string>(this.chatbotAIUrl, request).subscribe(
           response => answer =response
           )
         return answer;
  }

}
