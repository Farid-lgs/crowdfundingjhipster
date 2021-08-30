jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CommunityService } from '../service/community.service';
import { ICommunity, Community } from '../community.model';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';
import { CommunityMembersService } from 'app/entities/community-members/service/community-members.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';

import { CommunityUpdateComponent } from './community-update.component';

describe('Component Tests', () => {
  describe('Community Management Update Component', () => {
    let comp: CommunityUpdateComponent;
    let fixture: ComponentFixture<CommunityUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let communityService: CommunityService;
    let communityMembersService: CommunityMembersService;
    let userInfosService: UserInfosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CommunityUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CommunityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunityUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      communityService = TestBed.inject(CommunityService);
      communityMembersService = TestBed.inject(CommunityMembersService);
      userInfosService = TestBed.inject(UserInfosService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call communityMembers query and add missing value', () => {
        const community: ICommunity = { id: 456 };
        const communityMembers: ICommunityMembers = { id: 5705 };
        community.communityMembers = communityMembers;

        const communityMembersCollection: ICommunityMembers[] = [{ id: 65326 }];
        jest.spyOn(communityMembersService, 'query').mockReturnValue(of(new HttpResponse({ body: communityMembersCollection })));
        const expectedCollection: ICommunityMembers[] = [communityMembers, ...communityMembersCollection];
        jest.spyOn(communityMembersService, 'addCommunityMembersToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ community });
        comp.ngOnInit();

        expect(communityMembersService.query).toHaveBeenCalled();
        expect(communityMembersService.addCommunityMembersToCollectionIfMissing).toHaveBeenCalledWith(
          communityMembersCollection,
          communityMembers
        );
        expect(comp.communityMembersCollection).toEqual(expectedCollection);
      });

      it('Should call admin query and add missing value', () => {
        const community: ICommunity = { id: 456 };
        const admin: IUserInfos = { id: 71150 };
        community.admin = admin;

        const adminCollection: IUserInfos[] = [{ id: 52044 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: adminCollection })));
        const expectedCollection: IUserInfos[] = [admin, ...adminCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ community });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(adminCollection, admin);
        expect(comp.adminsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const community: ICommunity = { id: 456 };
        const communityMembers: ICommunityMembers = { id: 71374 };
        community.communityMembers = communityMembers;
        const admin: IUserInfos = { id: 62379 };
        community.admin = admin;

        activatedRoute.data = of({ community });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(community));
        expect(comp.communityMembersCollection).toContain(communityMembers);
        expect(comp.adminsCollection).toContain(admin);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Community>>();
        const community = { id: 123 };
        jest.spyOn(communityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ community });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: community }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(communityService.update).toHaveBeenCalledWith(community);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Community>>();
        const community = new Community();
        jest.spyOn(communityService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ community });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: community }));
        saveSubject.complete();

        // THEN
        expect(communityService.create).toHaveBeenCalledWith(community);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Community>>();
        const community = { id: 123 };
        jest.spyOn(communityService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ community });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(communityService.update).toHaveBeenCalledWith(community);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCommunityMembersById', () => {
        it('Should return tracked CommunityMembers primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommunityMembersById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
