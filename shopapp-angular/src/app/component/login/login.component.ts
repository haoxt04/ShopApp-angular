import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDTO } from '../../dto/user/login.dto';
import { UserService } from '../../service/user.service';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../response/user/login.response';
import { TokenService } from '../../service/token.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm
  phoneNumber: string = '';
  password: string = '';
  constructor(private router: Router, 
    private userService: UserService,
    private tokenService: TokenService
  ) {

  }

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  login() {
    const message =
      `phoneNumber : ${this.phoneNumber}` +
      `password: ${this.password}`;

    const loginDTO: LoginDTO = {
      "phone_number": this.phoneNumber,
      "password": this.password
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger
        const {token} = response
        this.tokenService.setToken(token);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        // xử lý lỗi nếu có
        alert(`Cannot login, error: ${error.error}`);
      },
    });
  }
}
