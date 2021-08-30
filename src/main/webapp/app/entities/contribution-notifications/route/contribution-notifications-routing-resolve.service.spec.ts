jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IContributionNotifications, ContributionNotifications } from '../contribution-notifications.model';
import { ContributionNotificationsService } from '../service/contribution-notifications.service';

import { ContributionNotificationsRoutingResolveService } from './contribution-notifications-routing-resolve.service';

describe('Service Tests', () => {
  describe('ContributionNotifications routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ContributionNotificationsRoutingResolveService;
    let service: ContributionNotificationsService;
    let resultContributionNotifications: IContributionNotifications | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ContributionNotificationsRoutingResolveService);
      service = TestBed.inject(ContributionNotificationsService);
      resultContributionNotifications = undefined;
    });

    describe('resolve', () => {
      it('should return IContributionNotifications returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContributionNotifications = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContributionNotifications).toEqual({ id: 123 });
      });

      it('should return new IContributionNotifications if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContributionNotifications = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultContributionNotifications).toEqual(new ContributionNotifications());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ContributionNotifications })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContributionNotifications = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContributionNotifications).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
