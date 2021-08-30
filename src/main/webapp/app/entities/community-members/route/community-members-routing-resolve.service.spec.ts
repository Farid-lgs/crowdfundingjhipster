jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICommunityMembers, CommunityMembers } from '../community-members.model';
import { CommunityMembersService } from '../service/community-members.service';

import { CommunityMembersRoutingResolveService } from './community-members-routing-resolve.service';

describe('Service Tests', () => {
  describe('CommunityMembers routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CommunityMembersRoutingResolveService;
    let service: CommunityMembersService;
    let resultCommunityMembers: ICommunityMembers | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CommunityMembersRoutingResolveService);
      service = TestBed.inject(CommunityMembersService);
      resultCommunityMembers = undefined;
    });

    describe('resolve', () => {
      it('should return ICommunityMembers returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityMembers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCommunityMembers).toEqual({ id: 123 });
      });

      it('should return new ICommunityMembers if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityMembers = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCommunityMembers).toEqual(new CommunityMembers());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CommunityMembers })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCommunityMembers = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCommunityMembers).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
