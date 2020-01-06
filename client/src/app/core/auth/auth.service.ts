import { Injectable } from "@angular/core";
import { User } from '../models/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginData } from '../models/login-data.model';

const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    

    constructor(private http: HttpClient) { }

    private webApiUrl = `${environment.webApiUrl}login`;

    login(loginData: LoginData): Observable<User> { 
        return this.http.post<User>(this.webApiUrl, loginData, httpOptions);
    }
}