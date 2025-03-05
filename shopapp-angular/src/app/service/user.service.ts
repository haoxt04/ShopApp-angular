import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dto/user/register.dto';
import { LoginDTO } from '../dto/user/login.dto';
import { environment } from '../environment/environment';
import { HttpUtilService } from './http.util.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;

  constructor(
    private http: HttpClient,
    private httpUtilService: HttpUtilService
  ) {}

  register(registerDTO: RegisterDTO): Observable<any> {
    const apiConfig = { headers: this.httpUtilService.createHeaders() };
    return this.http.post(this.apiRegister, registerDTO, apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    const apiConfig = { headers: this.httpUtilService.createHeaders() };
    return this.http.post(this.apiLogin, loginDTO, apiConfig);
  }
}

