jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICommunityNotifications, CommunityNotifications } from '../community-notifications.model';
import { CommunityNotificationsService } from '../service/community-notifications.service';

import { CommunityNotificationsRoutingResolveService } from './community-notifications-routing-resolve.service';

describe('Service Tests', () => {
  describe('CommunityNotifications routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CommunityNotificationsRoutingResolveService;
    let service: CommunityNotificationsService;
    let resultCommunityNotifications: ICommunityNotifications | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CommunityNotificationsRoutingResolveService);
      service = TestBed.inject(CommunityNotificationsService);
      resultCommunityNotifications = undefined;
    });

    describe('resolve', () => {
      it('should return ICommunityNotifications returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityNotifications = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCommunityNotifications).toEqual({ id: 123 });
      });

      it('should return new ICommunityNotifications if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityNotifications = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCommunityNotifications).toEqual(new CommunityNotifications());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CommunityNotifications })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityNotifications = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCommunityNotifications).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
