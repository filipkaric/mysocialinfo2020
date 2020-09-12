import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { LoginData } from '../../models/login-data.model';
import { Store, Select } from '@ngxs/store';
import * as authActions from '../../auth/auth.actions';
import { AuthState } from '../../auth/auth.state';
import { Observable } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  @Select(AuthState.getUser)
  loggedUser$: Observable<User>;
  loggedUser: User;

  registerForm: FormGroup;

  constructor(
    private store: Store,
    private router: Router
  ) { }

  login() {
    // const username = this.registerForm.get('username').value;
    const password = this.registerForm.get('password').value;
    const email = this.registerForm.get('email').value;
    const name = this.registerForm.get('name').value;
    let user = <User>{};
    user.email = email;
    user.name = name;
    user.password = password;
    this.store.dispatch(new authActions.RegisterUserAction(user));
  }

  back() {
    this.router.navigate(['/'])
  }

  ngOnInit() {
    this.registerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordConfirm: new FormControl('', Validators.required)
    })
        

    this.loggedUser$.subscribe(result =>
      {
        this.loggedUser = result;
      })
  }
}
