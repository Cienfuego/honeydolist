import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login';
import { HelloComponent } from './pages/hello';
import { RegisterComponent } from './pages/register';
import { EditProfileComponent } from './pages/edit-profile';


export const routes: Routes = [
  //{ path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'hello', component: HelloComponent },
  { path: 'register', component: RegisterComponent },
  { path: "edit-profile", component: EditProfileComponent}
];
