import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDTO } from '../../dto/user/login.dto';
import { UserService } from '../../service/user.service';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../response/user/login.response';
import { TokenService } from '../../service/token.service';
import { RoleService } from '../../service/role.service';
import { Role } from '../model/role';

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

  roles: Role[] = [];  // Mảng role
  rememberMe: boolean = true;
  selectedRole: Role | undefined;   // Biến lưu giá trị từ dropdown

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  constructor(private router: Router, 
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) { }

  ngOnInit() {
    // Gọi API lấy danh sách roles và lưu vào biến roles
    debugger
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      error: (error: any) => {
        debugger
        console.error('Error getting roles: ', error);
      }
    });
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
