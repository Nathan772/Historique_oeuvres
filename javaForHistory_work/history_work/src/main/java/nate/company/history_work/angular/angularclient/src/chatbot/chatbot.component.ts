  import { Component, Input, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
  import { BrowserModule } from '@angular/platform-browser';
  import { UserService } from '../user/user_service/user-service.service';
  import Typewriter from 'typewriter-effect/dist/core';
  import {ChatbotService} from './chatbot_service/chatbot.service';
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

    //you need to put to false to enable type writer to be updated !!
     @ViewChild('typewriterElement', { static: false }) typewriterElement!: ElementRef;
     //@ViewChild('typewriterElementAnswer', { static: false }) typewriterElement!: ElementRef;


    aiName:string="ArtistAI";
    waitingChatBotAnswer = false;
    chatbotService:ChatbotService;
    textLastAIMessageContent = "Ask me about anything";
    //textReactionAnswerAIMessageContent = "";
     messages = [
        { text: this.textLastAIMessageContent, authorId: this.aiName}
      ];
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



    constructor(userService:UserService, chatbotService:ChatbotService) {
        //this.connection = io.connect('https://socket-chat-server-zbqlbrimfj.now.sh');
        //.connection.on('chat message', message => this.messages.push(message));
        console.log("on passe par le constructor du chatbot")
        this.userService = userService;
        this.chatbotService = chatbotService;
      }

      openChat() {
        this.isOpen = true;
      }

      closeChat() {
        this.isOpen = false;
      }

      /*
      async is neccessary to enable await,
      await is necessary to wait server (llm) response
      */
      async sendMessage() {
        console.log("on entre dans send message");
        console.log("la phrase enregistrée est : "+this.typeMessageUser)
        this.waitingChatBotAnswer = true;
        console.log("la valeur de disable keyboard dans sendmessage : "+this.waitingChatBotAnswer)
        if (this.textLastAIMessageContent.trim() !== "") {
          //this.connection.emit('chat message', { text: this.textareaValue, authorId: "Domka" });
          /*
          add user request
          */
          this.messages.push({text:this.typeMessageUser , authorId:this.userService.userAccount.pseudo});

          //ignore chatbot answer
          //this.messages.push({text:"OK, la réponse est... Demande-moi ce que tu veux !",authorId:this.aiName });
          //do actual response

           /* await handle asychronous response
                      for the retrieve of the user
                      source : https://stackoverflow.com/questions/63136965/how-to-wait-for-function-with-subscribe-to-finish-in-angular-8
                      */

        //wait for response of the llm
         // let responseFromLLM = '{"content":"failure for json"}'
        //message for the moment the AI is thinking its answer
          this.textLastAIMessageContent = "I'm thinking 2"
         try {
           //disable user keyboard while waiting for answer
            this.waitingChatBotAnswer = true;
           let responseFromLLM =
            await this.chatbotService.sendRequestToChatbot("I am the questioner, my name is : "+
              this.userService.userAccount.pseudo+", my question is : "+this.typeMessageUser).toPromise() || '{"content": "failure for json"}'

          /*
          it works out with a valid json.
          */
            //let myJsonResponse = JSON.parse(responseFromLLM)
            let myJsonResponse = Object.values(responseFromLLM)[0]

              console.log("la réponse du llm est : "+myJsonResponse)

            this.messages.push({text:myJsonResponse,authorId:this.aiName });

            const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
                  //loop: true,
                  delay: 75,
                });

            this.textLastAIMessageContent = myJsonResponse

            //message associated to the response to the request
            //this.textLastMessageContent = '...'
            /*
            run at the same
            placeholder after the first message of the After
            THE FIRST message of the user
            rather than user last
            message */
            typewriter
                  .typeString(this.aiName+ " (chatbot) "+ "says : ")
                  .pauseFor(1500)
                  //.deleteAll()
                  .typeString(" "+this.textLastAIMessageContent)
                  .start();
             //disable user keyboard while waiting for answer

            this.typeMessageUser = '';
            //message after request
            //this.textLastMessageContent = ''
            }
          catch {
              //let myJsonResponse = JSON.parse(responseFromLLM)
                          let myJsonResponse = "Je suis désolé j'ai un problème avec mon serveur qui me sert de cerveau... Réessayez plus tard !"

                            console.log("la réponse en cas d'échec de requête est : "+myJsonResponse)

                          this.messages.push({text:myJsonResponse,authorId:this.aiName });

                          const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
                                //loop: true,
                                delay: 75,
                              });

                          this.textLastAIMessageContent = myJsonResponse
         /*
                    run at the same
                    placeholder after the first message of the After
                    THE FIRST message of the user
                    rather than user last
                    message */
                    typewriter
                          .typeString(this.aiName+ " (chatbot) "+ "says : ")
                          .pauseFor(1500)
                          //.deleteAll()
                          .typeString(" "+this.textLastAIMessageContent)
                          .start();

                    this.typeMessageUser = '';
                    }
          }
          //enable to answer again
          //after the response is ready but only it's ready
          this.waitingChatBotAnswer = false;


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

      //type writer for questions
      //write the waiting sentences and the beginning sentence
      /*if(!this.hasStarted){
        //define the beginning sentence
        console.log("on passe dans le hasStarted");
        this.textLastAIMessageContent = "Ask me about anything related to art !"
        this.hasStarted = true;
      }*/
    /*
    define the waiting sentence
    */
    /*
      else{
        this.textLastAIMessageContent = "(I'm thinking)"

      }*/
      //enable to remove old content
      typewriter.erasable = true

      typewriter
        .typeString(this.aiName+" (chatbot) : ")
        .pauseFor(1500)
        //.deleteAll()
        .typeString(" "+this.textLastAIMessageContent)
        .pauseFor(1500)
        .start();

        /*

        update for next turn

        */

          /*
          not working properb
          */
        /*
        enable to not remove old content
        */

        //not working properly
        /*
        this.textLastMessageContent = "(I'm thinking)"
        */
        /*
        typewriter
                .typeString(this.aiName+" (chatbot) : ")
                .pauseFor(1500)
                //.deleteAll()
                .typeString(" "+this.textLastMessageContent)
                .pauseFor(1500)*/
    }

  }


