import { User } from '../models/user.model';
import { LoginData } from '../models/login-data.model';
import { ɵCodegenComponentFactoryResolver } from '@angular/core';

export class LoginUsernamePasswordAction {
    static type = '[Auth] Login by username and password';
    constructor( public loginData: LoginData) { }
}

export class LoginSuccessAction {
    static type = '[Auth] Login success';
    constructor ( public user: User) { }
}

export class LoginFailedAction {
    static type = '[Auth] Login failed';
    constructor ( public user: User) { }
}

export class LoginFacebookAction{
    static type = '[Auth] Login with Facebook';
    constructor ( public code: String) { }
}

export class LoginYoutubeAction{
    static type = '[Auth] Login with Youtube';
    constructor ( public code: String) { }
}

export class GetTwitterLoginUrlAction{
    static type = '[Auth] Get twitter login url';
    constructor () { }
}

export class LoginTwitter {
    static type = '[Auth] Login twitter';
    constructor( public verifier: String, public token: String ){} 
}