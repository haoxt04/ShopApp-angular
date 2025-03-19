import { Component, OnInit } from '@angular/core';
import { CartService } from '../../service/cart.service';
import { ProductService } from '../../service/product.service';
import { Product } from '../model/product';
import { environment } from '../../environment/environment';

@Component({
  selector: 'app-order-confirm',
  standalone: false,
  templateUrl: './order-confirm.component.html',
  styleUrl: './order-confirm.component.scss'
})
export class OrderConfirmComponent implements OnInit {
  cartItems: { product: Product, quantity : number} [] = [];
  couponCode: string = '';  // Mã giảm giá
  totalAmount: number = 0;  // tổng tiền

  constructor (
    private cartService : CartService,
    private productService : ProductService
   ) {}

  ngOnInit(): void {
    // Lấy danh sách sản phẩm từ phần giỏ hàng
    debugger
    const cart = this.cartService.getCart();
    const productIds = Array.from(cart.keys());   // chuyển danh sách id từ Map giỏ hàng
    // Gọi service để lấy thông tin sản phẩm dựa trên id
    debugger
    this.productService.getProductByIds(productIds).subscribe({
      next: (products) => {
        debugger
        // Lấy thông tin sản phẩm và số lượng từ danh sách sản phẩm trong giỏ hàng
        this.cartItems = productIds.map((productId) => {
          debugger
          const product = products.find((p) => p.id === productId);
          if(product) {
            product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          }
          return {
            product: product!,
            quantity: cart.get(productId)!
          };
        });
        console.log("...");
      },
      complete: () => {
        debugger;
        this.calculateTotal()
      },
      error: (error:any) => {
        debugger;
        console.error('Error fetching detail', error);
      }
    });
  }
  // Hàm tính tổng tiền
  calculateTotal():void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }
}
