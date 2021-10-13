import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICreditCard, CreditCard } from '../credit-card.model';
import { CreditCardService } from '../service/credit-card.service';
import {IUserInfos, UserInfos} from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import {AccountService} from "../../../core/auth/account.service";

@Component({
  selector: 'jhi-credit-card-update',
  templateUrl: './credit-card-update.component.html',
})
export class CreditCardUpdateComponent implements OnInit {
  isSaving = false;
  IUserInfos = new UserInfos();

  userInfosCollection: IUserInfos[] = [];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required]],
    ownerName: [],
    key: [null, [Validators.required]],
    expirationDate: [],
    userInfos: [],
  });

  constructor(
    protected creditCardService: CreditCardService,
    protected userInfosService: UserInfosService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {

    this.activatedRoute.data.subscribe(({ creditCard }) => {

      if(this.accountService.userIdentity) {
        this.IUserInfos = new UserInfos(this.accountService.userIdentity.id);
      }

      if(creditCard !== undefined) {
        creditCard.userInfos = this.IUserInfos;
      } else {
        creditCard = new CreditCard(null, null, null, null, null, this.IUserInfos);
      }

      this.updateForm(creditCard);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const creditCard = this.createFromForm();
    if (creditCard.id == null) {
      this.subscribeToSaveResponse(this.creditCardService.create(creditCard));
    } else {
      this.subscribeToSaveResponse(this.creditCardService.update(creditCard));
    }
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditCard>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(creditCard: ICreditCard): void {
    this.editForm.patchValue({
      id: creditCard.id,
      number: creditCard.number,
      ownerName: creditCard.ownerName,
      key: creditCard.key,
      expirationDate: creditCard.expirationDate,
      userInfos: creditCard.userInfos,
    });

    this.userInfosCollection = this.userInfosService.addUserInfosToCollectionIfMissing(this.userInfosCollection, creditCard.userInfos);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfosService
      .query({ filter: 'creditcard-is-null' })
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('userInfos')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.userInfosCollection = userInfos));
  }

  protected createFromForm(): ICreditCard {
    return {
      ...new CreditCard(),
      id: this.editForm.get(['id'])!.value,
      number: this.editForm.get(['number'])!.value,
      ownerName: this.editForm.get(['ownerName'])!.value,
      key: this.editForm.get(['key'])!.value,
      expirationDate: this.editForm.get(['expirationDate'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
    };
  }
}
