import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import * as dayjs from "dayjs";

export type EntityResponseType = HttpResponse<string>;
@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  paymentHandler:any = null;
  stripeToken: any;
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/charge');

  constructor(public http: HttpClient, public applicationConfigService: ApplicationConfigService) { /*do nothing*/}

  paymentBack(amount: number | undefined, token: string, email: string): void {
    console.log(amount, token, email);
    console.log("aaaaaaaaaaaaaaaaaaaaaaaaa");
    console.log(this.resourceUrl);

    this.http.post(this.resourceUrl, `${amount as unknown as string} ${email} ${token}`, {observe: 'response'})
      .subscribe(
        () => {
          console.log("in subscribe");
        }
      );

    console.log("après post");
  }

  // payment(amount: number, http: HttpClient = this.http, resourceUrl = this.resourceUrl): any {
    // /*const paymentHandler = */(<any>window).StripeCheckout.configure({
    //   key: 'pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo',
    //   locale: 'auto',
    //   token(stripeToken: any) {
    //     console.log(stripeToken)
    //     console.log(stripeToken.id)
    //     alert('Stripe token generated!');
    //     // paymentService.test();
    //     console.log(resourceUrl);
    //     http.post(resourceUrl, {amount, token: stripeToken.id, receipt_email: stripeToken.email}, {observe: 'response'})
    //       .pipe(map((res: any) => console.log(res)));
    //     // console.log(this.paymentService.payment(amount));
    //     console.log("eeeeeeeeeeee");
    //   }
    // }).open({
    //   name: 'Positronx',
    //   description: '3 widgets',
    //   amount: amount * 100
    // });

    // const paymentHandler = (<any>window).StripeCheckout.configure({
    //   key: 'pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo',
    //   locale: 'auto',
    //   token(stripeToken: any): string {
    //     console.log(stripeToken)
    //     console.log(stripeToken.id)
    //     this.stripeToken = stripeToken;
    //     // alert('Stripe token generated!');
    //     // this.paymentService.payment();
    //     return stripeToken.id as string;

        // const testing = function(this: any): string {
        //   return this.stripeToken.id as string;
        // }
        //
        //
        // // const testing = (): string => {
        // //   // console.log("CA MARCHE !!!!");
        // //   // console.log(this.resourceUrl);
        // //
        // //   return this.stripeToken.id as string;
        // //   // this.http.post(this.resourceUrl).pipe(map((res: EntityResponseType) => console.log(res)));
        // // }
        //
        // // this.http.post(this.resourceUrl).pipe(map((res: EntityResponseType) => console.log(res)));
        // console.log(this.stripeToken.id);
        // // return this.stripeToken.id;
        // return setTimeout(function() {
        //   return testing()
        // }, 1000);
        // console.log("eeeeeeeeeeee");
      // }
    // });

    // paymentHandler.open({
    //   name: 'Positronx',
    //   description: '3 widgets',
    //   amount: amount * 100
    // });
    // this.test();
  // }

  // invokeStripe(): void {
  //   if(!window.document.getElementById('stripe-script')) {
  //     const script = window.document.createElement("script");
  //     script.id = "stripe-script";
  //     script.type = "text/javascript";
  //     script.src = "https://checkout.stripe.com/checkout.js";
  //     script.onload = () => {
  //       this.paymentHandler = (<any>window).StripeCheckout.configure({
  //         key: 'pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo',
  //         locale: 'auto',
  //         token(stripeToken: any) {
  //           console.log(stripeToken)
  //           alert('Payment has been successfull!');
  //         }
  //       });
  //     }
  //
  //     window.document.body.appendChild(script);
  //   }
  // }
  test(): void {
    console.log("YESSSSSSSS")
  }

}
