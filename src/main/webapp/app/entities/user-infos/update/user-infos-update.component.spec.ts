jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UserInfosService } from '../service/user-infos.service';
import { IUserInfos, UserInfos } from '../user-infos.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';
import { CommunityMembersService } from 'app/entities/community-members/service/community-members.service';

import { UserInfosUpdateComponent } from './user-infos-update.component';

describe('Component Tests', () => {
  describe('UserInfos Management Update Component', () => {
    let comp: UserInfosUpdateComponent;
    let fixture: ComponentFixture<UserInfosUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let userInfosService: UserInfosService;
    let userService: UserService;
    let communityMembersService: CommunityMembersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UserInfosUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(UserInfosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserInfosUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      userInfosService = TestBed.inject(UserInfosService);
      userService = TestBed.inject(UserService);
      communityMembersService = TestBed.inject(CommunityMembersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const userInfos: IUserInfos = { id: 456 };
        const user: IUser = { id: 20972 };
        userInfos.user = user;

        const userCollection: IUser[] = [{ id: 97813 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CommunityMembers query and add missing value', () => {
        const userInfos: IUserInfos = { id: 456 };
        const communityMembers: ICommunityMembers = { id: 85179 };
        userInfos.communityMembers = communityMembers;

        const communityMembersCollection: ICommunityMembers[] = [{ id: 12625 }];
        jest.spyOn(communityMembersService, 'query').mockReturnValue(of(new HttpResponse({ body: communityMembersCollection })));
        const additionalCommunityMembers = [communityMembers];
        const expectedCollection: ICommunityMembers[] = [...additionalCommunityMembers, ...communityMembersCollection];
        jest.spyOn(communityMembersService, 'addCommunityMembersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        expect(communityMembersService.query).toHaveBeenCalled();
        expect(communityMembersService.addCommunityMembersToCollectionIfMissing).toHaveBeenCalledWith(
          communityMembersCollection,
          ...additionalCommunityMembers
        );
        expect(comp.communityMembersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const userInfos: IUserInfos = { id: 456 };
        const user: IUser = { id: 27731 };
        userInfos.user = user;
        const communityMembers: ICommunityMembers = { id: 57488 };
        userInfos.communityMembers = communityMembers;

        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(userInfos));
        expect(comp.usersSharedCollection).toContain(user);
        expect(comp.communityMembersSharedCollection).toContain(communityMembers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserInfos>>();
        const userInfos = { id: 123 };
        jest.spyOn(userInfosService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: userInfos }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(userInfosService.update).toHaveBeenCalledWith(userInfos);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserInfos>>();
        const userInfos = new UserInfos();
        jest.spyOn(userInfosService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: userInfos }));
        saveSubject.complete();

        // THEN
        expect(userInfosService.create).toHaveBeenCalledWith(userInfos);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserInfos>>();
        const userInfos = { id: 123 };
        jest.spyOn(userInfosService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userInfos });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(userInfosService.update).toHaveBeenCalledWith(userInfos);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCommunityMembersById', () => {
        it('Should return tracked CommunityMembers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommunityMembersById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
