import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private messageSource = new Subject<Message>();
  public message$ = this.messageSource.asObservable();

  showMessage(message: Message) {
    this.messageSource.next(message);
  }
}

export interface Message {
  text: string;
  type: 'success' | 'error';
}