import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {Router} from "@angular/router";

export type EntityResponseType = HttpResponse<string>;
@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  paymentHandler:any = null;
  stripeToken: any;
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/charge');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService, protected router: Router) { /*do nothing*/}

  paymentBack(amount: number | undefined, token: string, email: string, projectId: number): void {

    const userName = localStorage.getItem('login') as string;

    this.http.post(this.resourceUrl, `${amount as unknown as string} ${email} ${token} ${projectId} ${userName}`, {observe: 'response', responseType: 'blob'})
      .subscribe(
        (res: any) => {
          if(typeof amount === 'number') {
            const amountStorage = amount + Number(localStorage.getItem('amount'));
            localStorage.setItem('amount', String(amountStorage))
          }
          window.location.reload();
        }
      );
  }
}
