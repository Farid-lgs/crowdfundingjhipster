import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectComment, ProjectComment } from '../project-comment.model';

import { ProjectCommentService } from './project-comment.service';

describe('Service Tests', () => {
  describe('ProjectComment Service', () => {
    let service: ProjectCommentService;
    let httpMock: HttpTestingController;
    let elemDefault: IProjectComment;
    let expectedResult: IProjectComment | IProjectComment[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProjectCommentService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        comment: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProjectComment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProjectComment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProjectComment', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ProjectComment', () => {
        const patchObject = Object.assign(
          {
            comment: 'BBBBBB',
          },
          new ProjectComment()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProjectComment', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ProjectComment', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProjectCommentToCollectionIfMissing', () => {
        it('should add a ProjectComment to an empty array', () => {
          const projectComment: IProjectComment = { id: 123 };
          expectedResult = service.addProjectCommentToCollectionIfMissing([], projectComment);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectComment);
        });

        it('should not add a ProjectComment to an array that contains it', () => {
          const projectComment: IProjectComment = { id: 123 };
          const projectCommentCollection: IProjectComment[] = [
            {
              ...projectComment,
            },
            { id: 456 },
          ];
          expectedResult = service.addProjectCommentToCollectionIfMissing(projectCommentCollection, projectComment);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ProjectComment to an array that doesn't contain it", () => {
          const projectComment: IProjectComment = { id: 123 };
          const projectCommentCollection: IProjectComment[] = [{ id: 456 }];
          expectedResult = service.addProjectCommentToCollectionIfMissing(projectCommentCollection, projectComment);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectComment);
        });

        it('should add only unique ProjectComment to an array', () => {
          const projectCommentArray: IProjectComment[] = [{ id: 123 }, { id: 456 }, { id: 17329 }];
          const projectCommentCollection: IProjectComment[] = [{ id: 123 }];
          expectedResult = service.addProjectCommentToCollectionIfMissing(projectCommentCollection, ...projectCommentArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const projectComment: IProjectComment = { id: 123 };
          const projectComment2: IProjectComment = { id: 456 };
          expectedResult = service.addProjectCommentToCollectionIfMissing([], projectComment, projectComment2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectComment);
          expect(expectedResult).toContain(projectComment2);
        });

        it('should accept null and undefined values', () => {
          const projectComment: IProjectComment = { id: 123 };
          expectedResult = service.addProjectCommentToCollectionIfMissing([], null, projectComment, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectComment);
        });

        it('should return initial array if no ProjectComment is added', () => {
          const projectCommentCollection: IProjectComment[] = [{ id: 123 }];
          expectedResult = service.addProjectCommentToCollectionIfMissing(projectCommentCollection, undefined, null);
          expect(expectedResult).toEqual(projectCommentCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
