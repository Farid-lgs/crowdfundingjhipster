import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAddress, Address } from '../address.model';
import { AddressService } from '../service/address.service';
import {IUserInfos, UserInfos} from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import {AccountService} from "../../../core/auth/account.service";

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  IUserInfos = new UserInfos();

  userInfosCollection: IUserInfos[] = [];
  countriesSharedCollection: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    address: [],
    city: [],
    state: [],
    zipCode: [],
    phoneNumber: [],
    userInfos: [],
    country: [],
  });

  constructor(
    protected addressService: AddressService,
    protected userInfosService: UserInfosService,
    protected countryService: CountryService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {

    this.activatedRoute.data.subscribe(({ address }) => {
      if(this.accountService.userIdentity) {
        this.IUserInfos = new UserInfos(this.accountService.userIdentity.id);
      }

      address.userInfos = this.IUserInfos;

      this.updateForm(address);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  trackUserInfosById(index: number, item: IUserInfos): number {
    return item.id!;
  }

  trackCountryById(index: number, item: ICountry): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    console.log(address);
    this.editForm.patchValue({
      id: address.id,
      address: address.address,
      city: address.city,
      state: address.state,
      zipCode: address.zipCode,
      phoneNumber: address.phoneNumber,
      userInfos: address.userInfos,
      country: address.country,
    });

    this.userInfosCollection = this.userInfosService.addUserInfosToCollectionIfMissing(this.userInfosCollection, address.userInfos);
    this.countriesSharedCollection = this.countryService.addCountryToCollectionIfMissing(this.countriesSharedCollection, address.country);
  }

  protected loadRelationshipsOptions(): void {
    this.userInfosService
      .query({ filter: 'address-is-null' })
      .pipe(map((res: HttpResponse<IUserInfos[]>) => res.body ?? []))
      .pipe(
        map((userInfos: IUserInfos[]) =>
          this.userInfosService.addUserInfosToCollectionIfMissing(userInfos, this.editForm.get('userInfos')!.value)
        )
      )
      .subscribe((userInfos: IUserInfos[]) => (this.userInfosCollection = userInfos));
    this.countryService
      .query()
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing(countries, this.editForm.get('country')!.value))
      )
      .subscribe((countries: ICountry[]) => (this.countriesSharedCollection = countries));
  }

  protected createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      city: this.editForm.get(['city'])!.value,
      state: this.editForm.get(['state'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      userInfos: this.editForm.get(['userInfos'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }
}
