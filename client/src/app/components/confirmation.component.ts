import { Component, inject, OnInit } from '@angular/core';
import { PaymentService } from '../payment.service';
import { PaymentReceipt } from '../models';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  // TODO: Task 5
  private ps = inject(PaymentService);
  private cs = inject(CartStore);

  receipt!: PaymentReceipt

  ngOnInit(): void {
    this.receipt = this.ps.paymentReceipt;
    
  }

  resetStore(){
    this.cs.resetCart();
  }

  


}
