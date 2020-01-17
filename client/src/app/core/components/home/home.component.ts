import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Store } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  code: String;
  routeEventSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private store: Store,
    private router: Router
    ) { }

  ngOnInit() {
    debugger
    this.routeEventSubscription = this.route.queryParams.subscribe(params => {
      if (params.code != null && params.code !== undefined) {
        debugger
        this.router.url;
        this.code = params['code'];
        this.store.dispatch(new authActions.LoginFacebookAction(this.code));
      }
    })
  }

  facebookLogin() {
    window.location.href = environment.facebookLoginUrl;

  }

  ngOnDestroy() {
    if (this.routeEventSubscription !== undefined) {
      this.routeEventSubscription.unsubscribe();
    }
  }

}
