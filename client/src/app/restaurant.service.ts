import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Item, LoginRequest } from "./models";

@Injectable({
	providedIn: 'root'
  })
export class RestaurantService {

  private http = inject(HttpClient);
  

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<any> {
    return this.http.get<Item[]>('/api/getmenu')
  }

  // TODO: Task 3.2
  postLogin(login: LoginRequest): Observable<any> {
    return this.http.post<LoginRequest>('/api/food_order', login)
  }

}
