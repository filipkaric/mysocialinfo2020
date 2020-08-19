import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxsModule } from '@ngxs/store';
import { environment } from 'src/environments/environment';
import { NgxsStoragePluginModule } from '@ngxs/storage-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { AuthService } from './core/auth/auth.service';
import { SharedMaterialModule } from './shared/shared-material.module';
import { LoginComponent } from './core/components/login/login.component';
import { AuthState } from './core/auth/auth.state';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthGuard } from './core/auth/auth.guard';
import { HomeComponent } from './core/components/home/home.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RegisterComponent } from './core/components/register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgxChartsModule,
    HttpClientModule,
    ReactiveFormsModule,
    SharedMaterialModule,
    FlexLayoutModule,
    NgxsModule.forRoot([AuthState], {developmentMode: !environment.production}),
    NgxsStoragePluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot({logger: console, collapsed: false, disabled: environment.production})
  ],
  providers: [
    AuthService,
    AuthGuard  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
