import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Item, LoginRequest, PaymentReceipt } from "./models";

@Injectable({
    providedIn: 'root'
  })
export class PaymentService {

    paymentReceipt!: PaymentReceipt;

    updateReceipt(receipt: PaymentReceipt){
        this.paymentReceipt = receipt;
    }


}