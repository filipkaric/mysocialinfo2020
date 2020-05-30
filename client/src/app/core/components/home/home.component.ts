import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { environment } from 'src/environments/environment';
import { SocialData } from '../../models/social-data.model';
import { GraphData } from '../../models/graph-data.model';

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

  colorScheme = {
    domain: ['#9370DB', '#87CEFA', '#FA8072', '#FF7F50', '#90EE90', '#9370DB']
  };

  //pie
  showLabels = true;

  // data goes here
  public single = [
    {
      "name": "China",
      "value": 2243772
    },
    {
      "name": "USA",
      "value": 1126000
    },
    {
      "name": "Norway",
      "value": 296215
    },
    {
      "name": "Japan",
      "value": 257363
    },
    {
      "name": "Germany",
      "value": 196750
    },
    {
      "name": "France",
      "value": 204617
    }
  ];

  public multi = [
    {
      "name": "China",
      "series": [
        {
          "name": "2018",
          "value": 2243772
        },
        {
          "name": "2017",
          "value": 1227770
        }
      ]
    },

    {
      "name": "USA",
      "series": [
        {
          "name": "2018",
          "value": 1126000
        },
        {
          "name": "2017",
          "value": 764666
        }
      ]
    },

    {
      "name": "Norway",
      "series": [
        {
          "name": "2018",
          "value": 296215
        },
        {
          "name": "2017",
          "value": 209122
        }
      ]
    },

    {
      "name": "Japan",
      "series": [
        {
          "name": "2018",
          "value": 257363
        },
        {
          "name": "2017",
          "value": 205350
        }
      ]
    },

    {
      "name": "Germany",
      "series": [
        {
          "name": "2018",
          "value": 196750
        },
        {
          "name": "2017",
          "value": 129246
        }
      ]
    },

    {
      "name": "France",
      "series": [
        {
          "name": "2018",
          "value": 204617
        },
        {
          "name": "2017",
          "value": 149797
        }
      ]
    }
  ];

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
    private changeDetectorRef: ChangeDetectorRef
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
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
      this.changeDetectorRef.detectChanges();
      }
    });
    this.twitterSubscription = this.twitterData$.subscribe(result => {
      if(result) {
        debugger
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
      this.changeDetectorRef.detectChanges();
      }
    });
    this.youtubeSubscription = this.youtubeData$.subscribe(result => {
      if(result) {
        debugger
      const data = <GraphData>{
        name: result.socialNetwork,
        value: result.numberOfPosts
      }
      this.numberOfPostsGraphData.push(data);
      this.numberOfPostsGraphData = [...this.numberOfPostsGraphData];
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
