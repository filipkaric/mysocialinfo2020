import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { UserProfile } from '../../models/user-profile-model';
import { SocialNetwork } from '../../models/social-network.enum';

@Component({
  selector: 'app-user-profiles',
  templateUrl: './user-profiles.component.html',
  styleUrls: ['./user-profiles.component.css']
})
export class UserProfilesComponent implements OnInit {

  userProfileFacebook: UserProfile;
  userProfileYoutube: UserProfile;
  userProfileTwitter: UserProfile;

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService.getUserProfile(SocialNetwork.FACEBOOK).subscribe(result => {
      this.userProfileFacebook = result;
    });
    this.authService.getUserProfile(SocialNetwork.YOUTUBE).subscribe(result => {
      this.userProfileYoutube = result;
    });
    this.authService.getUserProfile(SocialNetwork.TWITTER).subscribe(result => {
      this.userProfileTwitter = result;
    });
  }

  back() {
    this.router.navigate(['/home'])
  }

}
