import { Injectable } from "@angular/core";
import { User } from '../models/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginData } from '../models/login-data.model';
import { SocialData } from '../models/social-data.model';
import { UserProfile } from '../models/user-profile-model';
import { SocialNetwork } from '../models/social-network.enum';

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

    register(user: User): Observable<User> { 
      return this.http.post<User>(this.webApiUrl + "save", user, httpOptions);
    }

    facebookLogin(code: String): Observable<SocialData> {
      return this.http.get<SocialData>(this.webApiUrl + "facebook?code=" + code);
    }

    getTwitterUrl(): Observable<User> {
      return this.http.get<User>(this.webApiUrl + "twitter");
    }

    twitterLogin(verifier: String, token: String): Observable<SocialData> {
      return this.http.get<SocialData>(this.webApiUrl + "twitterLogin?verifier=" + verifier + "&token=" + token);
    }

    youtubeLogin(code: String): Observable<SocialData> {
      return this.http.get<SocialData>(this.webApiUrl + "youtube?code=" + code);
    }

    logout(): Observable<boolean> {
      return this.http.post<boolean>(this.webApiUrl + "test", '');
    }

    getUserProfile(socialNetwork: number): Observable<UserProfile>{
      return this.http.get<UserProfile>(this.webApiUrl + "getUserProfile?socialNetwork=" + socialNetwork.toString());
    }

    getSocialData(socialNetwork: number): Observable<SocialData>{
      return this.http.get<SocialData>(this.webApiUrl + "getSocialData?socialNetwork=" + socialNetwork.toString());
    }
}