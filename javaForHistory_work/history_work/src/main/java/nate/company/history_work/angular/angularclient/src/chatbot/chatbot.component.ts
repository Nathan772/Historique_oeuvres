import { Component, Input, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../user/user_service/user-service.service';
import Typewriter from 'typewriter-effect/dist/core';
//import Typewriter from 't-writer.js';
/*import * as io from 'socket.io-client';
import {Socket} from 'socket.io-client';
*/


@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.css'
})
export class ChatbotComponent implements OnInit {

   @ViewChild('typewriterElement', { static: true }) typewriterElement!: ElementRef;

  aiName:string="ArtisteAI";
   messages = [
      { text: "Demande-moi ce que tu veux !", authorId: this.aiName}
    ];

    textLastMessageContent = 'Demandes-moi ce que tu veux !';
    messageIntro = 'écris-lui !';
    /*
    it needs
    to be set to empty everytime
    you're done with it,
    in order
    to enable
    the box to show default message.
    */
    typeMessageUser = "";
    isOpen = false;
    startWriting: boolean = false
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
        this.messages.push({text:this.typeMessageUser , authorId:this.userService.userAccount.pseudo});


        this.messages.push({text:"OK, la réponse est... Demande-moi ce que tu veux !",authorId:this.aiName });

        const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
              //loop: true,
              delay: 75,
            });

        typewriter
              .typeString(this.aiName)
              .pauseFor(1500)
              //.deleteAll()
              .typeString(" "+this.textLastMessageContent)
              .pauseFor(1500)
              .start();

        this.typeMessageUser = '';

      }
    }
ngOnInit() {
   // Start after 1 second + 1 second of startDelay
      //setTimeout(() => this.startWriting = true, 1000)}

//necessary for progressive writing
       /*@ViewChild('tw') typewriterElement;
       const target = this.typewriterElement.nativeElement
       const writer = new Typewriter(target, {
            loop: true,
            typeColor: 'blue'
          })*/
}


//necessary for progressive writing
  ngAfterViewInit(): void {
    const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
      //loop: true,
      delay: 75,
    });

    typewriter
      .typeString(this.aiName)
      .pauseFor(1500)
      //.deleteAll()
      .typeString(" "+this.textLastMessageContent)
      .pauseFor(1500)
      .start();
  }

}


