import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dto/user/register.dto';
import { LoginDTO } from '../dto/user/login.dto';
import { environment } from '../environment/environment';
import { HttpUtilService } from './http.util.service';
import { UserResponse } from '../response/user/user.reponse';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

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

  getUserDetail(token: string) {
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        'Content-Type:': 'application/json',
        Authorization: `Bearer ${token}`
      })
    })
  }

  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    try {
      if(userResponse == null || userResponse == undefined) {
        return;
      }
      // Convert the userResponse object to a JSON string
      const userResponseJSON = JSON.stringify(userResponse);

      // Save the JSON string to local storage with a key (...UserResponse)
      localStorage.setItem('user', userResponseJSON);

      console.log('User response saved to local storage');
    }catch(error) {
      console.error('Error saving user response to local storage', error);
    }
  }

  getUserResponseFromLocalStorage() {
    try {
      // Retrieve the JSON string from local storage user the key
      const userResponseJSON = localStorage.getItem('user')
      if(userResponseJSON == null || userResponseJSON == undefined) {
        return;
      }
      // Parse the JSON string back to an object
      const userResponse = JSON.parse(userResponseJSON);
      console.log('User response retrieved from local storage')
      return userResponse;
    } catch(error) {
      console.error('Error retrieved from local storage');
      return null;  // Return null or handle the error as needed
    }
  }
}

