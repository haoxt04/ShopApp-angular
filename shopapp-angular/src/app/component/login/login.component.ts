import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDTO } from '../../dto/user/login.dto';
import { UserService } from '../../service/user.service';
import { NgForm } from '@angular/forms';
import { LoginResponse } from '../../response/user/login.response';
import { TokenService } from '../../service/token.service';
import { RoleService } from '../../service/role.service';
import { Role } from '../model/role';
import { UserResponse } from '../../response/user/user.reponse';

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
  userResponse?: UserResponse;

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
    console.log("login đã được gọi")
    const message =
      `phoneNumber : ${this.phoneNumber}` +
      `password: ${this.password}`;

    const loginDTO: LoginDTO = {
      "phone_number": this.phoneNumber,
      "password": this.password
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const token = response.token;   // Lấy token từ reponse
        
        this.userService.getUserDetail(token).subscribe({
          next: (response: any) => {
            debugger;
            this.userResponse = {
              ...response,
              date_of_birth: new Date(response.date_of_birth),
            };
            this.userService.saveUserResponseToLocalStorage(this.userResponse);
            this.router.navigate(["/"]);
          },
          error: (error: any) => {
            alert(`Cannot login, error:  ${error.error}`)
          }
        });
      }
    });
  }
}
