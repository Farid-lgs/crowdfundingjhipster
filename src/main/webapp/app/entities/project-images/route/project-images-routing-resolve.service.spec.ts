jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProjectImages, ProjectImages } from '../project-images.model';
import { ProjectImagesService } from '../service/project-images.service';

import { ProjectImagesRoutingResolveService } from './project-images-routing-resolve.service';

describe('Service Tests', () => {
  describe('ProjectImages routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProjectImagesRoutingResolveService;
    let service: ProjectImagesService;
    let resultProjectImages: IProjectImages | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProjectImagesRoutingResolveService);
      service = TestBed.inject(ProjectImagesService);
      resultProjectImages = undefined;
    });

    describe('resolve', () => {
      it('should return IProjectImages returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectImages = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectImages).toEqual({ id: 123 });
      });

      it('should return new IProjectImages if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectImages = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProjectImages).toEqual(new ProjectImages());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectImages })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectImages = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectImages).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
