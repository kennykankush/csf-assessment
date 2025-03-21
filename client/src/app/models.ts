// You may use this file to create any models
export interface Item {
    id: string,
    name: string,
    price: number,
    description: string,
    quantity: number

}

export interface ItemOrder {

    id: string,
    name: string,
    price: number,
    quantity: number

}

export interface LoginRequest {
    username: string,
    password: string,
    items: ItemOrder[]
}

export interface PaymentReceipt {
    timestamp: number,
    order_id: string,
    payment_id: string,
    total: number
}
