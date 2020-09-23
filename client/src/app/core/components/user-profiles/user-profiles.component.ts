import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { UserProfile } from '../../models/user-profile-model';
import { SocialNetwork } from '../../models/social-network.enum';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Store } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { KeyValue } from '../../models/key-value.model';

@Component({
  selector: 'app-user-profiles',
  templateUrl: './user-profiles.component.html',
  styleUrls: ['./user-profiles.component.css']
})
export class UserProfilesComponent implements OnInit {

  userProfileFacebook: UserProfile = <UserProfile>{};
  userProfileYoutube: UserProfile = <UserProfile>{};
  userProfileTwitter: UserProfile = <UserProfile>{};
  private webApiUrl = `${environment.webApiUrl}`;
  facebookProfileValues: KeyValue[] = [];
  twitterProfileValues: KeyValue[] = [];
  youtubeProfileValues: KeyValue[] = [];


  formFacebook: FormGroup;
  formTwitter: FormGroup;
  formYoutube: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef,
    private store: Store,
    private http: HttpClient
  ) {
    this.formFacebook = formBuilder.group({
      first_name:[this.userProfileFacebook.first_name],
      last_name:[this.userProfileFacebook.last_name],
      email:[this.userProfileFacebook.email],
      location:[this.userProfileFacebook.location],
      birthday:[this.userProfileFacebook.birthday],
      screen_name:[this.userProfileFacebook.screen_name],
      socialNetworkId: [this.userProfileFacebook.socialNetworkId]
    });
    this.formTwitter = formBuilder.group({
      first_name:[this.userProfileTwitter.first_name],
      last_name:[this.userProfileTwitter.last_name],
      email:[this.userProfileTwitter.email],
      location:[this.userProfileTwitter.location],
      birthday:[this.userProfileTwitter.birthday],
      screen_name:[this.userProfileTwitter.screen_name],
      socialNetworkId: [this.userProfileTwitter.socialNetworkId]
    });
    this.formYoutube = formBuilder.group({
      first_name:[this.userProfileYoutube.first_name],
      last_name:[this.userProfileYoutube.last_name],
      email:[this.userProfileYoutube.email],
      location:[this.userProfileYoutube.location],
      birthday:[this.userProfileYoutube.birthday],
      screen_name:[this.userProfileYoutube.screen_name],
      socialNetworkId: [this.userProfileYoutube.socialNetworkId]
    });
  }

  ngOnInit() {
    this.http.get<UserProfile>(this.webApiUrl + "getUserProfileAsString?socialNetwork=" + SocialNetwork.FACEBOOK.toString()).subscribe(result => {
        for(var key in result) {
          var value = result[key];
          let keyValue = <KeyValue>{
            name: key,
            value: value
          };
          this.facebookProfileValues.push(keyValue);
          }
          this.facebookProfileValues = [...this.facebookProfileValues];
        }
      );
    this.http.get<UserProfile>(this.webApiUrl + "getUserProfileAsString?socialNetwork=" + SocialNetwork.TWITTER.toString()).subscribe(result => {
        for(var key in result) {
          var value = result[key];
          let keyValue = <KeyValue>{
            name: key,
            value: value
          };
          this.twitterProfileValues.push(keyValue);
          }
          this.twitterProfileValues = [...this.twitterProfileValues];
        }
      );
      this.http.get<UserProfile>(this.webApiUrl + "getUserProfileAsString?socialNetwork=" + SocialNetwork.YOUTUBE.toString()).subscribe(result => {
        for(var key in result) {
          var value = result[key];
          let keyValue = <KeyValue>{
            name: key,
            value: value
          };
          this.youtubeProfileValues.push(keyValue);
          }
          this.youtubeProfileValues = [...this.youtubeProfileValues];
        }
      );
  }

  logout(){
    this.store.dispatch(new authActions.LogoutAction());
  }

  back() {
    this.router.navigate(['/home'])
  }

}
