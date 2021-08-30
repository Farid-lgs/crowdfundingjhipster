jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUserInfos, UserInfos } from '../user-infos.model';
import { UserInfosService } from '../service/user-infos.service';

import { UserInfosRoutingResolveService } from './user-infos-routing-resolve.service';

describe('Service Tests', () => {
  describe('UserInfos routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: UserInfosRoutingResolveService;
    let service: UserInfosService;
    let resultUserInfos: IUserInfos | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(UserInfosRoutingResolveService);
      service = TestBed.inject(UserInfosService);
      resultUserInfos = undefined;
    });

    describe('resolve', () => {
      it('should return IUserInfos returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUserInfos = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUserInfos).toEqual({ id: 123 });
      });

      it('should return new IUserInfos if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUserInfos = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultUserInfos).toEqual(new UserInfos());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as UserInfos })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUserInfos = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUserInfos).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
