import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { UserProfile } from '../../models/user-profile-model';
import { SocialNetwork } from '../../models/social-network.enum';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-profiles',
  templateUrl: './user-profiles.component.html',
  styleUrls: ['./user-profiles.component.css']
})
export class UserProfilesComponent implements OnInit {

  userProfileFacebook: UserProfile = <UserProfile>{};
  userProfileYoutube: UserProfile = <UserProfile>{};
  userProfileTwitter: UserProfile = <UserProfile>{};

  formFacebook: FormGroup;
  formTwitter: FormGroup;
  formYoutube: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef
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
    this.authService.getUserProfile(SocialNetwork.FACEBOOK).subscribe(result => {
      this.userProfileFacebook = result;
      setTimeout(() => {
        this.formFacebook.patchValue(this.userProfileFacebook),
        this.changeDetectorRef.detectChanges();
      }, 500
      )
      

    });
    this.authService.getUserProfile(SocialNetwork.YOUTUBE).subscribe(result => {
      this.userProfileYoutube = result;
      setTimeout(() => {
        this.formYoutube.patchValue(this.userProfileYoutube),
        this.changeDetectorRef.detectChanges();
      }, 500
      )
    });
    this.authService.getUserProfile(SocialNetwork.TWITTER).subscribe(result => {
      this.userProfileTwitter = result;
      setTimeout(() => {
        this.formTwitter.patchValue(this.userProfileTwitter),
        this.changeDetectorRef.detectChanges();
      }, 500
      )
    });
  }

  back() {
    this.router.navigate(['/home'])
  }

}
