import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './core/components/login/login.component';
import { AuthGuard } from './core/auth/auth.guard';
import { HomeComponent } from './core/components/home/home.component';
import { RegisterComponent } from './core/components/register/register.component';
import { UserProfilesComponent } from './core/components/user-profiles/user-profiles.component';


const routes: Routes = [
  {
    path:'',
    canActivate: [AuthGuard],
    component: HomeComponent
  },
  { 
    path: 'login', 
    component: LoginComponent 
  },
  { 
    path: 'register', 
    component: RegisterComponent 
  },
  {
    path: 'home',
    children: [
      { path: '',  canActivate: [AuthGuard], component: HomeComponent },
      { path: 'facebook/:code', canActivate: [AuthGuard], component: HomeComponent },
      { path: 'youtube/:code', canActivate: [AuthGuard], component: HomeComponent },
      { path: 'twitter/:oauth_token&:oauth_verifier', canActivate: [AuthGuard], component: HomeComponent }
    ]
  },
  {
    path: 'home/:code',
    canActivate: [AuthGuard],
    component: HomeComponent
  },
  {
    path: 'user-profile',
    canActivate: [AuthGuard],
    component: UserProfilesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
