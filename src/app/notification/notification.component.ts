import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Notification, NotificationType } from '../models/notification';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.less']
})
export class NotificationComponent implements OnInit, OnDestroy {

  notifications: Notification[] = [];
  notificationSubscription: Subscription;

  constructor(private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.notificationSubscription = this.notificationService
    .observableNotification()
    .subscribe(notification => {
      if (!notification.message) {
        this.notifications.forEach(a => this.remove(a));
        return;
      }

      this.notifications.push(notification);

      if (notification.autoClose) {
        setTimeout(() => this.remove(notification), 3000);
      }
    });
  }

  ngOnDestroy(): void {
    this.notificationSubscription.unsubscribe();
  }

  remove(notification: Notification): void {
    if (!this.notifications.includes(notification)) {
      return;
    }

    this.notifications = this.notifications.filter(x => x !== notification);
  }

  css(notification: Notification): string {
    if (!notification) {
      return;
    }

    const classes = ['notification'];

    const notificationTypeClass = {
      [NotificationType.Success]: 'notification-success',
      [NotificationType.Error]: 'notification-error',
    };

    classes.push(notificationTypeClass[notification.type]);

    return classes.join(' ');
  }
}
