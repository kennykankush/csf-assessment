import { Component, inject, Input, OnInit } from '@angular/core';
import { RestaurantService } from '../restaurant.service';
import { Item, ItemOrder } from '../models';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{
  // TODO: Task 2
  private rs = inject(RestaurantService);
  private store = inject(CartStore);
  menu!: Item[];
  totalPrice! : number;
  totalOrder! : number;
  items$!: ItemOrder[];

  ngOnInit(): void {
    this.fetchMenu();
    this.checkCart();
  }

  fetchMenu(){
    this.rs.getMenuItems().subscribe({
      next: (response) => {
        this.menu = response;
        console.log("SERVICE:GETMENUU() -> GOT THE MENU FROM CONTROLLER", response);
      },
      error: (error) => console.log(error),
      complete: () => console.log("SERVICE: GETMENU() -> COMPLETED")
    })

  }

  addQuantity(item: Item){
    item.quantity++
  }

  deleteQuantity(item: Item){
    item.quantity--
  }

  addToCart(item: Item){
    item.quantity++

    const itemOrder: ItemOrder = {
      id: item.id,
      name: item.name,
      price: item.price,
      quantity: item.quantity
    }

    console.log(item);
    console.log(itemOrder);
    console.log(this.items$);
    this.store.addToCart(itemOrder)
    console.log(this.items$);
  }

  checkCart(){
    console.log('Checking in');
    this.store.itemOrders$.subscribe(
      (items) => {
        this.items$ = items;
        this.totalOrder = items.length;

        items.forEach(
          item => {
            this.totalPrice=+ item.price * item.quantity;
          }
        )
      }
    )
    
  }
}
