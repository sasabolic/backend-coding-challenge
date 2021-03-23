import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { VatComponent } from './vat/vat.component';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { LogoutComponent } from './logout/logout.component';
import { AuthGuard } from './auth.guard';
import { NotificationComponent } from './notification/notification.component';
import { ErrorInterceptor } from './interceptors/error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    ExpensesComponent,
    VatComponent,
    LoginComponent,
    LogoutComponent,
    NotificationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: ExpensesComponent, canActivate: [AuthGuard] },
      { path: 'login', component: LoginComponent },
    ])
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
