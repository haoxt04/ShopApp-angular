import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { RegisterDTO } from '../dto/register.dto';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  // khai báo các biến tương ứng với các trường dữ liệu trong form
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor(private router: Router, private userService: UserService) { 
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);

    // inject
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`)

  }

  register() {
    const message = `phone : ${this.phone}`
                  + `password: ${this.password}`
                  + `retypePassword: ${this.retypePassword}`
                  + `fullName: ${this.fullName}`
                  + `address: ${this.address}`
                  + `isAccepted: ${this.isAccepted}`
                  + `dateOfBirth: ${this.dateOfBirth}`;
    //alert(message);
    
    const registerDTO:RegisterDTO = {
      "fullName": this.fullName,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
    }
    this.userService.register(registerDTO).subscribe({
      next: (response: any)  => {
        debugger
        // xử lý kết quả trả về khi đăng ký thành công
        if(response && (response.status === 200 || response.status === 201)) {
          // đăng ký thành công chuyển sang màn hình login
          this.router.navigate(['/login']);
        }else {
          // xử lý trường hợp đăng ký không thành công nếu cần
        }
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        // xử lý lỗi nếu có
        alert(`Cannot register, error: ${error.error}`)
      }
    });
  }

    // kiểm tra 2 mật khẩu match nhau
  checkPasswordsMatch() {
    if(this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch': true});
    }else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
  
  checkAge() {
    if(this.dateOfBirth) {
      const today = new Date();
      const birthDate = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if(monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
  
      if(age < 18) {
        this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge': true});
      }else {
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
