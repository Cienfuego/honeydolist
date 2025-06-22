// import { inject } from '@angular/core';
// import { CanActivateFn, Router } from '@angular/router';
// import { AuthService } from '../services/auth';

// export const authGuard: CanActivateFn = (route, state) => {
//   const authService = inject(AuthService);
//   const router = inject(Router);

//   if (authService.isLoggedIn()) {
//     return true;
//   } 
//   if (state.url !== '/login') {
//     router.navigate(['/']);
//   }
//   //else {
//   //  router.navigate(['/login']);
//   //  return false;
//   return false;
// };
