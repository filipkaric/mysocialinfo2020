import { Injectable } from "@angular/core";
import { User } from '../models/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginData } from '../models/login-data.model';
import { SocialData } from '../models/social-data.model';

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

    private webApiUrl = `${environment.webApiUrl}`;

    login(loginData: LoginData): Observable<User> { 
        return this.http.post<User>(this.webApiUrl + "login", loginData, httpOptions);
    }

    facebookLogin(code: String): Observable<SocialData> {
      return this.http.get<SocialData>(this.webApiUrl + "facebook?code=" + code);
    }

    getTwitterUrl(): Observable<User> {
      return this.http.get<User>(this.webApiUrl + "twitter");
    }

    twitterLogin(verifier: String, token: String): Observable<User> {
      return this.http.get<User>(this.webApiUrl + "twitterLogin?verifier=" + verifier + "&token=" + token);
    }

    youtubeLogin(code: String): Observable<SocialData> {
      return this.http.get<SocialData>(this.webApiUrl + "youtube?code=" + code);
    }
}