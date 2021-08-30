jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProjectComment, ProjectComment } from '../project-comment.model';
import { ProjectCommentService } from '../service/project-comment.service';

import { ProjectCommentRoutingResolveService } from './project-comment-routing-resolve.service';

describe('Service Tests', () => {
  describe('ProjectComment routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProjectCommentRoutingResolveService;
    let service: ProjectCommentService;
    let resultProjectComment: IProjectComment | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProjectCommentRoutingResolveService);
      service = TestBed.inject(ProjectCommentService);
      resultProjectComment = undefined;
    });

    describe('resolve', () => {
      it('should return IProjectComment returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectComment = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectComment).toEqual({ id: 123 });
      });

      it('should return new IProjectComment if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectComment = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProjectComment).toEqual(new ProjectComment());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProjectComment })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProjectComment = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProjectComment).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
