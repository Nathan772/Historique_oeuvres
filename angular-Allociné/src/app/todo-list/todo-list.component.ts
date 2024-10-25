import { Component, OnInit } from '@angular/core';
import { Todo } from '../model/todo';


@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css'],
})
export class TodoListComponent implements OnInit {
  todos: Todo[] = [
    { label: 'Faire les courses', done: false },
    { label: 'Sport', done: true },
  ];

  inputValue: string = '';

  addTodoItem() {
    this.todos.push({ label: this.inputValue, done: false });
  }

  constructor() {}

  ngOnInit() {}
}
