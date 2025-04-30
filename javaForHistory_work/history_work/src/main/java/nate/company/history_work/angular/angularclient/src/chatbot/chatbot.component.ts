import { Component, Input, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../user/user_service/user-service.service';
/*import * as io from 'socket.io-client';
import {Socket} from 'socket.io-client';
*/


@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.css'
})
export class ChatbotComponent implements OnInit {
  aiName:string="ArtisteAI";
   messages = [
      { text: "Demande-moi ce que tu veux !", authorId: 'aiName' }
    ];

    textLastMessageContent = '';
    isOpen = false;
    userService:UserService;

    //connection:Socket;



  constructor(userService:UserService) {
      //this.connection = io.connect('https://socket-chat-server-zbqlbrimfj.now.sh');
      //.connection.on('chat message', message => this.messages.push(message));
      console.log("on passe par le constructor du chatbot")
      this.userService = userService;
    }

    openChat() {
      this.isOpen = true;
    }

    closeChat() {
      this.isOpen = false;
    }

    sendMessage() {
      if (this.textLastMessageContent.trim() !== "") {
        //this.connection.emit('chat message', { text: this.textareaValue, authorId: "Domka" });
        /*
        add user request
        */
        this.messages.push({text:this.textLastMessageContent , authorId:this.userService.userAccount.pseudo});
        this.messages.push({text:"OK, la réponse est... Demande-moi ce que tu veux !",authorId:this.aiName })
        this.textLastMessageContent = '';

      }
    }
ngOnInit() {}
}

