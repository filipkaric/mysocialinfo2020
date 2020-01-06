import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { LoginData } from '../../models/login-data.model';
import { Store, Select } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { AuthState } from '../../auth/auth.state';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Select(AuthState.getUser)
  loggedUser$: Observable<User>;
  loggedUser: User;

  loginForm: FormGroup;

  constructor(
    private store: Store
  ) { }

  login() {
    const username = this.loginForm.get('username').value;
    const password = this.loginForm.get('username').value;
    let loginData = <LoginData>{ username: username, password: password};
    this.store.dispatch(new authActions.LoginUsernamePasswordAction(loginData));
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
        

    this.loggedUser$.subscribe(result =>
      {
        this.loggedUser = result;
      })
  }

}
