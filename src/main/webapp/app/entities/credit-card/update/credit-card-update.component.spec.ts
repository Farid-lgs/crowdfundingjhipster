jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CreditCardService } from '../service/credit-card.service';
import { ICreditCard, CreditCard } from '../credit-card.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';

import { CreditCardUpdateComponent } from './credit-card-update.component';

describe('Component Tests', () => {
  describe('CreditCard Management Update Component', () => {
    let comp: CreditCardUpdateComponent;
    let fixture: ComponentFixture<CreditCardUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let creditCardService: CreditCardService;
    let userInfosService: UserInfosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CreditCardUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CreditCardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CreditCardUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      creditCardService = TestBed.inject(CreditCardService);
      userInfosService = TestBed.inject(UserInfosService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userInfos query and add missing value', () => {
        const creditCard: ICreditCard = { id: 456 };
        const userInfos: IUserInfos = { id: 71446 };
        creditCard.userInfos = userInfos;

        const userInfosCollection: IUserInfos[] = [{ id: 55861 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfosCollection })));
        const expectedCollection: IUserInfos[] = [userInfos, ...userInfosCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ creditCard });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(userInfosCollection, userInfos);
        expect(comp.userInfosCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const creditCard: ICreditCard = { id: 456 };
        const userInfos: IUserInfos = { id: 7466 };
        creditCard.userInfos = userInfos;

        activatedRoute.data = of({ creditCard });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(creditCard));
        expect(comp.userInfosCollection).toContain(userInfos);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CreditCard>>();
        const creditCard = { id: 123 };
        jest.spyOn(creditCardService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ creditCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: creditCard }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(creditCardService.update).toHaveBeenCalledWith(creditCard);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CreditCard>>();
        const creditCard = new CreditCard();
        jest.spyOn(creditCardService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ creditCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: creditCard }));
        saveSubject.complete();

        // THEN
        expect(creditCardService.create).toHaveBeenCalledWith(creditCard);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CreditCard>>();
        const creditCard = { id: 123 };
        jest.spyOn(creditCardService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ creditCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(creditCardService.update).toHaveBeenCalledWith(creditCard);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserInfosById', () => {
        it('Should return tracked UserInfos primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserInfosById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
