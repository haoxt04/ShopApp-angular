import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  // khai báo các biến tương ứng với các trường dữ liệu trong form
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor() {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
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
    alert(message);
  }
}
