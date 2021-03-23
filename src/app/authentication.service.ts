import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { Authentication } from './models/authentication';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
    private authenticationSubject: BehaviorSubject<Authentication>;
    private authentication: Observable<Authentication>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.authenticationSubject = new BehaviorSubject<Authentication>(JSON.parse(localStorage.getItem('authentication')));
        this.authentication = this.authenticationSubject.asObservable();
    }

    public get authenticationValue(): Authentication {
        return this.authenticationSubject.value;
    }

    login(username, password): Observable<Authentication> {
        return this.http.post<Authentication>(`${environment.apiUrl}/login`, { username, password })
            .pipe(map(authentication => {
                localStorage.setItem('authentication', JSON.stringify(authentication));
                this.authenticationSubject.next(authentication);
                return authentication;
            }));
    }

    logout(): void {
        localStorage.removeItem('authentication');
        this.authenticationSubject.next(null);
        this.router.navigate(['/login']);
    }
}
