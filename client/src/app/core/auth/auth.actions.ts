import { User } from '../models/user.model';
import { LoginData } from '../models/login-data.model';

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