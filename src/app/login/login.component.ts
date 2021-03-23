import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
  }

  login(): void {
    this.notificationService.clear();
    this.authenticationService.login(this.username, this.password)
    .subscribe(
      () => {
        this.router.navigate(['/']);
      },
      () => {
        this.resetCredentials();
        this.notificationService.error('Invalid username or password', {autoClose: false});
      });
  }

  private resetCredentials(): void {
    this.username = null;
    this.password = null;
  }
}
