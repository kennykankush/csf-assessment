import { ComponentStore } from "@ngrx/component-store";
import { Injectable } from "@angular/core";
import { Item, ItemOrder } from "../models";


export interface ItemOrderState {
    itemsSlice: ItemOrder[],
}

// TODO Task 2
@Injectable({
    providedIn: 'root'
})

// Use the following class to implement your store

export class CartStore extends ComponentStore<ItemOrderState>{

    constructor(){
        super ({ itemsSlice: []});
    }

    //Selector
    readonly itemOrders$ = this.select(state => state.itemsSlice);
    
    //Updaters 
    //thought process is when it adds, check if item exist and override
    readonly addToCart = this.updater((state, item: ItemOrder) => ({
    ...state, 
    itemsSlice: [...state.itemsSlice, item]
    }));

    //increaseItem(newItem: number){
    // if (!this.itemNames.has(newItem)){
    //     this.itemNames.add(newItem)
  
    //     const index = this.y.findIndex(item => item===newItem)
  
    //     let n = this.y[index]
  
        // increase qty
    //   }
    // }

    readonly resetCart = this.updater(() => ({itemsSlice: []}));
}