import { State, Selector, Action, StateContext } from '@ngxs/store';
import { User } from '../models/user.model';
import * as authActions from './auth.actions';
import { AuthService } from './auth.service';
import { catchError, tap } from 'rxjs/operators';

export interface AuthStateModel {
    isAuthenticated: boolean;
    user: User;
}

@State<AuthStateModel>({
    name:'auth',
    defaults: {
        isAuthenticated: false,
        user: null
    }
})

export class AuthState {

    constructor(private authService: AuthService) { }

    @Selector()
    static isAuthenticated(state: AuthStateModel): boolean {
        return state.isAuthenticated;
    }

    @Selector()
    static getUser(state: AuthStateModel): User | null {
        return state.user;
    }

    @Action(authActions.LoginUsernamePasswordAction)
    loginUsernamePassword(ctx: StateContext<AuthStateModel>, action: authActions.LoginUsernamePasswordAction) {
        return this.authService.login(action.loginData).pipe(
            tap((user: User) => ctx.dispatch(new authActions.LoginSuccessAction(user))),
            catchError(error => {return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LoginSuccessAction)
    loginSuccess({patchState}: StateContext<AuthStateModel>, action: authActions.LoginSuccessAction) {
        if(action.user) {
            patchState({
                isAuthenticated: true,
                user: action.user
            })
        }
    }
}