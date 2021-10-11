import {Component, Input, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReward } from '../reward.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { RewardService } from '../service/reward.service';
import { RewardDeleteDialogComponent } from '../delete/reward-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import {CreatorService} from "../../../shared/service/creator.service";
import {PaymentService} from "../../../shared/service/payment.service";
import {map} from "rxjs/operators";
import {ApplicationConfigService} from "../../../core/config/application-config.service";

@Component({
  selector: 'jhi-reward',
  templateUrl: './reward.component.html',
})
export class RewardComponent implements OnInit {
  rewards?: IReward[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  projectId = 0;
  creator = false;
  paymentHandler:any = null;
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/charge');

  // @Input() creator: boolean | undefined;

  constructor(
    protected rewardService: RewardService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: DataUtils,
    protected router: Router,
    protected modalService: NgbModal,
    protected creatorService: CreatorService,
    protected paymentService: PaymentService,protected http: HttpClient, protected applicationConfigService: ApplicationConfigService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.creator = this.creatorService.isCreator();

    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;
    // Récupère l'url + split la chaine et retourne le 3è fragment (équivalent au projectId)
    // this.projectId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.projectId = Number(this.router.url.split('/')[2]);
    // console.log(this.projectId);
    this.rewardService
      .query(this.projectId,{
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IReward[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        () => {
          this.isLoading = false;
          this.onError();
        }
      );
  }

  ngOnInit(): void {
    this.invokeStripe();
    // this.paymentService.invokeStripe();
    this.handleNavigation();
  }

  makePayment(amount: number | undefined, paymentService: PaymentService = this.paymentService): void {
    if(typeof amount === 'undefined') {
      amount = 0;
    }
    // this.paymentService.payment(amount);
    // console.log(this.paymentService.payment(amount));

    /*const paymentHandler = */(<any>window).StripeCheckout.configure({
      key: 'pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo',
      locale: 'auto',
      token(stripeToken: any) {
        console.log(stripeToken)
        console.log(stripeToken.id)
        alert('Stripe token generated!');
        console.log(paymentService.paymentBack(amount, stripeToken.id, stripeToken.email));
        // console.log(paymentService.paymentBack(amount, stripeToken.id, stripeToken.email));


        // this.http.post(this.resourceUrl).pipe(map((res: any) => console.log(res)));
        // console.log(this.paymentService.payment(amount));
        console.log("eeeeeeeeeeee");
      }
    }).open({
      name: 'Positronx',
      description: '3 widgets',
      amount: amount * 100
    });

    // this.paymentService.test();
// console.log("ttttttttt");
//     paymentHandler.open({
//       name: 'Positronx',
//       description: '3 widgets',
//       amount: amount * 100
//     });
  }

  invokeStripe(): void {
    if(!window.document.getElementById('stripe-script')) {
      const script = window.document.createElement("script");
      script.id = "stripe-script";
      script.type = "text/javascript";
      script.src = "https://checkout.stripe.com/checkout.js";
      script.onload = () => {
        this.paymentHandler = (<any>window).StripeCheckout.configure({
          key: 'pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo',
          locale: 'auto',
          token(stripeToken: any) {
            console.log(stripeToken)
            alert('Payment has been successfull!');
          }
        });
      }

      window.document.body.appendChild(script);
    }
  }

  trackId(index: number, item: IReward): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(reward: IReward): void {
    const modalRef = this.modalService.open(RewardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reward = reward;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IReward[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/reward'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.rewards = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
