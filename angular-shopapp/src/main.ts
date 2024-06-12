import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { HomeComponent } from './app/home/home.component';
import { OrderComponent } from './app/order/order.component';
import { OrderCofirmComponent } from './app/order-cofirm/order-cofirm.component';
import { LoginComponent } from './app/login/login.component';
import { RegisterComponent } from './app/register/register.component';



bootstrapApplication(
  //HomeComponent,
  //OrderComponent,
  //OrderCofirmComponent,
  //LoginComponent,
  RegisterComponent,
  appConfig)
  .catch((err) => console.error(err));

