import { Component, OnInit } from '@angular/core';
import * as io from 'socket.io-client';
import {Socket} from 'socket.io-client';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.scss'
})
export class ChatbotComponent implements OnInit {
   messages = [
      { text: "bla", authorId: 'Domka' }
    ];
    textareaValue = '';
    isOpen = false;

    connection:Socket;

    ngOnInit() {}

  constructor() {
      this.connection = io.connect('https://socket-chat-server-zbqlbrimfj.now.sh');
      this.connection.on('chat message', message => this.messages.push(message));
    }

    openChat() {
      this.isOpen = true;
    }

    closeChat() {
      this.isOpen = false;
    }

    sendMessage() {
      if (this.textareaValue.trim() !== "") {
        this.connection.emit('chat message', { text: this.textareaValue, authorId: "Domka" });
        this.textareaValue = '';
      }
    }

}

