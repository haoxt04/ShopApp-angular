import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Category } from '../component/model/category';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiGetCategories  = `${environment.apiBaseUrl}/categories`;

  constructor(private http: HttpClient) { }
  getCategories(keyword: string, page: number, limit: number):Observable<Category[]> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('limit', limit.toString());     
      return this.http.get<{ status: number; message: string; data: Category[] }>(this.apiGetCategories, { params })
      .pipe(map(response => response.data));         
  }
}