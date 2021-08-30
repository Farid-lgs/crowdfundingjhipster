jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CommunityNotificationsService } from '../service/community-notifications.service';
import { ICommunityNotifications, CommunityNotifications } from '../community-notifications.model';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';

import { CommunityNotificationsUpdateComponent } from './community-notifications-update.component';

describe('Component Tests', () => {
  describe('CommunityNotifications Management Update Component', () => {
    let comp: CommunityNotificationsUpdateComponent;
    let fixture: ComponentFixture<CommunityNotificationsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let communityNotificationsService: CommunityNotificationsService;
    let communityService: CommunityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CommunityNotificationsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CommunityNotificationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunityNotificationsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      communityNotificationsService = TestBed.inject(CommunityNotificationsService);
      communityService = TestBed.inject(CommunityService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Community query and add missing value', () => {
        const communityNotifications: ICommunityNotifications = { id: 456 };
        const community: ICommunity = { id: 32335 };
        communityNotifications.community = community;

        const communityCollection: ICommunity[] = [{ id: 1005 }];
        jest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
        const additionalCommunities = [community];
        const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
        jest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ communityNotifications });
        comp.ngOnInit();

        expect(communityService.query).toHaveBeenCalled();
        expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(communityCollection, ...additionalCommunities);
        expect(comp.communitiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const communityNotifications: ICommunityNotifications = { id: 456 };
        const community: ICommunity = { id: 5320 };
        communityNotifications.community = community;

        activatedRoute.data = of({ communityNotifications });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(communityNotifications));
        expect(comp.communitiesSharedCollection).toContain(community);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityNotifications>>();
        const communityNotifications = { id: 123 };
        jest.spyOn(communityNotificationsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: communityNotifications }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(communityNotificationsService.update).toHaveBeenCalledWith(communityNotifications);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityNotifications>>();
        const communityNotifications = new CommunityNotifications();
        jest.spyOn(communityNotificationsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: communityNotifications }));
        saveSubject.complete();

        // THEN
        expect(communityNotificationsService.create).toHaveBeenCalledWith(communityNotifications);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityNotifications>>();
        const communityNotifications = { id: 123 };
        jest.spyOn(communityNotificationsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(communityNotificationsService.update).toHaveBeenCalledWith(communityNotifications);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCommunityById', () => {
        it('Should return tracked Community primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommunityById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
