import { Component, inject, OnInit } from '@angular/core';
import { ItemOrder, LoginRequest } from '../models';
import { CartStore } from './cart.store';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestaurantService } from '../restaurant.service';
import { PaymentService } from '../payment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{

  private store = inject(CartStore);
  private fb = inject(FormBuilder);
  private rs = inject(RestaurantService);
  private ps = inject(PaymentService);
  private router = inject(Router);

  items$!: ItemOrder[]
  totalPrice!: number
  loginForm!: FormGroup
  

  ngOnInit(): void {
    this.loadCart();
    this.loginForm = this.createForm();
      
  }

  loadCart(){
    this.store.itemOrders$.subscribe(
      (response) => {
        console.log(response);
        this.items$ = response
        response.forEach((item) => {
          this.totalPrice = item.price * item.quantity;
        });

      }
    );
  }
  // TODO: Task 3

  createForm(): FormGroup { 
    
      return this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]

    })
  }

  isValid(): boolean {
    return !this.loginForm.valid
  }

  onClick(): void {

    console.log('submitting')

    const loginRequest: LoginRequest= {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
      items: this.items$
    }

    console.log('submitting', loginRequest);

    this.rs.postLogin(loginRequest).subscribe({
      next: (response) => {
      this.ps.updateReceipt(response);
      this.router.navigate(['/success'])},
      error: (error) => alert(JSON.stringify(error)),
      complete:() => console.log('Complete')
    })
  }

  resetCart(){
    this.store.resetCart();
  }


}
