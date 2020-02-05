import { State, Selector, Action, StateContext } from '@ngxs/store';
import { User } from '../models/user.model';
import * as authActions from './auth.actions';
import { AuthService } from './auth.service';
import { catchError, tap } from 'rxjs/operators';
import { Navigate } from '@ngxs/router-plugin';
import { Router } from '@angular/router';
import { NgZone } from '@angular/core';

export interface AuthStateModel {
    isAuthenticated: boolean;
    user: User;
}

@State<AuthStateModel>({
    name: 'auth',
    defaults: {
        isAuthenticated: false,
        user: null
    }
})

export class AuthState {

    constructor(
        private authService: AuthService,
        private router: Router,
        private zone: NgZone
    ) { }

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
            tap((user: User) => {
                ctx.dispatch(new authActions.LoginSuccessAction(user))
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LoginSuccessAction)
    loginSuccess(ctx: StateContext<AuthStateModel>, action: authActions.LoginSuccessAction) {
        if (action.user) {
            ctx.patchState({
                isAuthenticated: true,
                user: action.user
            });
            this.zone.run(() =>
                this.router.navigate(['/home'])
            )
        }
    }

    @Action(authActions.LoginFacebookAction)
    loginFacebook(ctx: StateContext<AuthStateModel>, action: authActions.LoginFacebookAction) {
        return this.authService.facebookLogin(action.code).pipe(
            tap((user: User) => {
                console.log(user)
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.GetTwitterLoginUrlAction)
    getTwitterUrl(ctx: StateContext<AuthStateModel>, action: authActions.GetTwitterLoginUrlAction) {
        return this.authService.getTwitterUrl().pipe(
            tap((user: User) => {
                debugger
                window.location.href = 'https://api.twitter.com/oauth/authenticate?oauth_token=' + user.token;
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LoginTwitter)
    loginTwitter(ctx: StateContext<AuthStateModel>, action: authActions.LoginTwitter) {
        return this.authService.twitterLogin(action.verifier).pipe(
            tap((user: User) => {
                debugger
                
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }
}