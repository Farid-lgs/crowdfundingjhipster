jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProjectAccount, ProjectAccount } from '../project-account.model';
import { ProjectAccountService } from '../service/project-account.service';

import { ProjectAccountRoutingResolveService } from './project-account-routing-resolve.service';

describe('Service Tests', () => {
  describe('ProjectAccount routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProjectAccountRoutingResolveService;
    let service: ProjectAccountService;
    let resultProjectAccount: IProjectAccount | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProjectAccountRoutingResolveService);
      service = TestBed.inject(ProjectAccountService);
      resultProjectAccount = undefined;
    });

    describe('resolve', () => {
      it('should return IProjectAccount returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectAccount = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectAccount).toEqual({ id: 123 });
      });

      it('should return new IProjectAccount if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectAccount = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProjectAccount).toEqual(new ProjectAccount());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectAccount })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectAccount = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectAccount).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
