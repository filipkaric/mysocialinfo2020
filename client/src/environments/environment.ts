// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  webApiUrl: 'http://localhost:8080/',
  facebookAppId: '490841858175046',
  // facebookLoginUrl: 'https://www.facebook.com/dialog/oauth?client_id=490841858175046&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fhome%2F',
  facebookLoginUrl: 'https://www.facebook.com/dialog/oauth?client_id=490841858175046&scope=instagram_basic&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fhome%2Ffacebook%2F',
  instagramAppId: '181515839736452'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
