import { State, Selector, Action, StateContext } from '@ngxs/store';
import { User } from '../models/user.model';
import * as authActions from './auth.actions';
import { AuthService } from './auth.service';
import { catchError, tap } from 'rxjs/operators';
import { Navigate } from '@ngxs/router-plugin';
import { Router } from '@angular/router';
import { NgZone } from '@angular/core';
import { SocialData } from '../models/social-data.model';

export interface AuthStateModel {
    isAuthenticated: boolean;
    user: User;
    facebookData: SocialData;
    youtubeData: SocialData;
    twitterData: SocialData;
}

@State<AuthStateModel>({
    name: 'auth',
    defaults: {
        isAuthenticated: false,
        user: null,
        facebookData: null,
        youtubeData: null,
        twitterData: null
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
            tap((facebookData: SocialData) => {
                ctx.patchState({facebookData})
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
                window.location.href = 'https://api.twitter.com/oauth/authenticate?oauth_token=' + user.token;
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LoginTwitter)
    loginTwitter(ctx: StateContext<AuthStateModel>, action: authActions.LoginTwitter) {
        return this.authService.twitterLogin(action.verifier, action.token).pipe(
            tap((twitterData: SocialData) => {
                ctx.patchState({twitterData})
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LoginYoutubeAction)
    loginYoutube(ctx: StateContext<AuthStateModel>, action: authActions.LoginYoutubeAction) {
        return this.authService.youtubeLogin(action.code).pipe(
            tap((youtubeData: SocialData) => {
                ctx.patchState({youtubeData})
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.SocialHistoryDataAction)
    socialHistory(ctx: StateContext<AuthStateModel>, action: authActions.SocialHistoryDataAction) {
        if(action.socialNetwork === 0) {
            return this.authService.getSocialData(action.socialNetwork).pipe(
                tap((facebookData: SocialData) => {
                    ctx.patchState({facebookData})
                }),
                catchError(error => {
                    return ctx.dispatch(new authActions.LoginFailedAction(error))
                })
            )
        } else if(action.socialNetwork === 1) {
            return this.authService.getSocialData(action.socialNetwork).pipe(
            tap((youtubeData: SocialData) => {
                ctx.patchState({youtubeData})
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
        } else if(action.socialNetwork === 2){
            return this.authService.getSocialData(action.socialNetwork).pipe(
                tap((twitterData: SocialData) => {
                    ctx.patchState({twitterData})
                }),
                catchError(error => {
                    return ctx.dispatch(new authActions.LoginFailedAction(error))
                })
            )
        }
    }

    @Action(authActions.RegisterUserAction)
    register(ctx: StateContext<AuthStateModel>, action: authActions.RegisterUserAction) {
        return this.authService.register(action.user).pipe(
            tap((user: User) => {
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LogoutAction)
    logout(ctx: StateContext<AuthStateModel>, action: authActions.LogoutAction) {
        return this.authService.logout().pipe(
            tap((sucess: boolean) => {
                ctx.dispatch(new authActions.LogoutSuccessAction(sucess))
            }),
            catchError(error => {
                return ctx.dispatch(new authActions.LoginFailedAction(error))
            })
        )
    }

    @Action(authActions.LogoutSuccessAction)
    logoutSuccess(ctx: StateContext<AuthStateModel>, action: authActions.LogoutSuccessAction) {
        if (action.successful) {
            ctx.patchState({
                isAuthenticated: false,
                user: null,
                facebookData: null,
                youtubeData: null,
                twitterData: null
            });
            this.zone.run(() =>
                this.router.navigate(['/'])
            )
        }
    }
}