import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Notification, NotificationType } from './models/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
    private subject = new Subject<Notification>();

    observableNotification(): Observable<Notification> {
        return this.subject.asObservable();
    }

    success(message: string, options?: any): void {
      this.subject.next(new Notification({ ...options, type: NotificationType.Success, message }));
    }

    error(message: string, options?: any): void {
      this.subject.next(new Notification({ ...options, type: NotificationType.Error, message }));
    }

    clear(): void {
        this.subject.next(new Notification({  }));
    }
}
