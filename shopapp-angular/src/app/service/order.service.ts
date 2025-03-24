import { Injectable } from "@angular/core";
import { environment } from "../environment/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { OrderDTO } from "../dto/order/order.dto";
import { HttpHeaders } from '@angular/common/http';
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root',
  })
  export class OrderService {
    private apiUrl = `${environment.apiBaseUrl}/orders`;
  
    constructor(private http: HttpClient, 
      private tokenService: TokenService
    ) {}
  
    placeOrder(orderData: OrderDTO): Observable<any> {    
    const token = this.tokenService.getToken(); // Lấy token từ localStorage

    const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`, // Gửi token kèm request
        'Content-Type': 'application/json'
    });
      // Gửi yêu cầu đặt hàng
      return this.http.post(this.apiUrl, orderData, { headers });
    }

    getOrderById(orderId: number): Observable<any> {
      const url = `${environment.apiBaseUrl}/orders/${orderId}`;
      return this.http.get(url);
    }
  }