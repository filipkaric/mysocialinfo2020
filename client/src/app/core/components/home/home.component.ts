import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { environment } from 'src/environments/environment';
import { SocialData } from '../../models/social-data.model';
import { GraphData } from '../../models/graph-data.model';
import { MultiGraphData } from '../../models/multi-graph-data.model';
import { AuthService } from '../../auth/auth.service';
import { SocialNetwork } from '../../models/social-network.enum';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  //Charts

  title = 'Angular Charts';
  facebookData$: Observable<SocialData>;
  twitterData$: Observable<SocialData>;
  youtubeData$: Observable<SocialData>;
  facebookLoggedIn: boolean = false;
  twitterLoggedIn: boolean = false;
  youtubeLoggedIn: boolean = false;

  view: any[] = [600, 400];

  // options for the chart
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Друштвена мрежа';
  showYAxisLabel = true;
  yAxisLabel = 'Број постова';
  timeline = true;

  numberOfPostsGraphData: GraphData[] = [];
  multiGraphData: MultiGraphData[] = [];

  colorScheme = {
    domain: ['#3B5998', '#00ACEE', '#FF0000', '#FF7F50', '#90EE90', '#9370DB']
  };

  colorSchemeMulti = {
    domain: ['#9cc2ff', '#0062ff', '#002a6e']
  };

  //pie
  showLabels = true;

  //Charts

  code: String;
  twitterToken: String;
  routeEventSubscription: Subscription;
  facebookSubscription: Subscription;
  twitterSubscription: Subscription;
  youtubeSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private store: Store,
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    private service: AuthService
  ) {
    this.facebookData$ = this.store.select(state => state.auth.facebookData);
    this.twitterData$ = this.store.select(state => state.auth.twitterData);
    this.youtubeData$ = this.store.select(state => state.auth.youtubeData);
   }

  ngOnInit() {
    this.routeEventSubscription = this.route.queryParams.subscribe(params => {
      if (params.code != null && params.code !== undefined) {
        if (this.router.url.includes("facebook")) {
          this.code = params['code'];
          this.store.dispatch(new authActions.LoginFacebookAction(this.code));
        }
      }
      if (this.router.url.includes("twitter")) {
        this.code = params['oauth_verifier'];
        this.twitterToken = params['oauth_token'];
        this.store.dispatch(new authActions.LoginTwitter(this.code, this.twitterToken));
      }
      if (this.router.url.includes("youtube")) {
        this.code = params['code'];
        this.store.dispatch(new authActions.LoginYoutubeAction(this.code));
      }
    });
    this.facebookSubscription = this.facebookData$.subscribe(result => {
      if(result) {
      this.facebookLoggedIn = true;
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
      const multiData = <MultiGraphData>{
        name: result.socialNetwork,
        series: []
      }
      const regularData = <GraphData>{
        name: 'Тренутни месец',
        value: result.numberOfPostsCurrentMonth
      }
      const regularData1 = <GraphData>{
        name: 'Претходни месец',
        value: result.numberOfPostsLastMonth
      }
      const regularData2 = <GraphData>{
        name: 'Пре два месеца',
        value: result.numberOfPostsLastMonth
      }
      multiData.series.push(regularData);
      multiData.series.push(regularData1);
      multiData.series.push(regularData2);
      this.multiGraphData.push(multiData);
      this.multiGraphData = [...this.multiGraphData];
      this.changeDetectorRef.detectChanges();
      }
    });
    this.twitterSubscription = this.twitterData$.subscribe(result => {
      if(result) {
      this.twitterLoggedIn = true;
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
      const multiData = <MultiGraphData>{
        name: result.socialNetwork,
        series: []
      }
      const regularData = <GraphData>{
        name: 'Тренутни месец',
        value: result.numberOfPostsCurrentMonth
      }
      const regularData1 = <GraphData>{
        name: 'Претходни месец',
        value: result.numberOfPostsLastMonth
      }
      const regularData2 = <GraphData>{
        name: 'Пре два месеца',
        value: result.numberOfPostsLastMonth
      }
      multiData.series.push(regularData);
      multiData.series.push(regularData1);
      multiData.series.push(regularData2);
      this.multiGraphData.push(multiData);
      this.multiGraphData = [...this.multiGraphData];
      this.changeDetectorRef.detectChanges();
      }
    });
    this.youtubeSubscription = this.youtubeData$.subscribe(result => {
      if(result) {
      this.youtubeLoggedIn = true;
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
      const multiData = <MultiGraphData>{
        name: result.socialNetwork,
        series: []
      }
      const regularData = <GraphData>{
        name: 'Тренутни месец',
        value: result.numberOfPostsCurrentMonth
      }
      const regularData1 = <GraphData>{
        name: 'Претходни месец',
        value: result.numberOfPostsLastMonth
      }
      const regularData2 = <GraphData>{
        name: 'Пре два месеца',
        value: result.numberOfPostsLastMonth
      }
      multiData.series.push(regularData);
      multiData.series.push(regularData1);
      multiData.series.push(regularData2);
      this.multiGraphData.push(multiData);
      this.multiGraphData = [...this.multiGraphData];
      this.changeDetectorRef.detectChanges();
      }
    });
  }

  facebookLogin() {
    window.location.href = environment.facebookLoginUrl;
  }

  youtubeLogin() {
    window.location.href = environment.youtubeLoginUrl;
  }

  twitterLogin() {
    // window.location.href = 'https://api.twitter.com/oauth/authenticate?oauth_token=O2BdKgAAAAABCJUlAAABb_1eNaE';
    this.store.dispatch(new authActions.GetTwitterLoginUrlAction);
  }

  logout(){
    this.store.dispatch(new authActions.LogoutAction());
  }

  userProfiles(){
    this.router.navigate(['/user-profile'])
  }

  historyData(){
    this.service.getSocialData(SocialNetwork.FACEBOOK).subscribe(result => {
    });
    // this.twitterData$ = this.service.getSocialData(SocialNetwork.TWITTER);
    // this.youtubeData$ = this.service.getSocialData(SocialNetwork.YOUTUBE);
  }

  ngOnDestroy() {
    if (this.routeEventSubscription !== undefined) {
      this.routeEventSubscription.unsubscribe();
    }
    if (this.facebookSubscription !== undefined) {
      this.facebookSubscription.unsubscribe();
    }
    if (this.twitterSubscription !== undefined) {
      this.twitterSubscription.unsubscribe();
    }
    if (this.youtubeSubscription !== undefined) {
      this.youtubeSubscription.unsubscribe();
    }
  }

}
