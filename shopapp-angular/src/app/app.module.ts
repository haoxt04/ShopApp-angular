import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './component/home/home.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { OrderComponent } from './component/order/order.component';
import { DetailProductComponent } from './component/detail-product/detail-product.component';
import { OrderDetailComponent } from './component/order-detail/order.detail.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app/app.component';
import { TokenInterceptor } from './interceptor/token.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbPopoverModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    DetailProductComponent,
    OrderComponent,
    OrderDetailComponent,
    LoginComponent,
    RegisterComponent,
    AppComponent
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    NgbPopoverModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [provideHttpClient(), {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [
    AppComponent
    // HomeComponent,
    // DetailProductComponent,
    // OrderComponent,
    // OrderDetailComponent,
    // LoginComponent,
    // RegisterComponent
  ]
})
export class AppModule { }
