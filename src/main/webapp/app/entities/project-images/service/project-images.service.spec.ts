import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectImages, ProjectImages } from '../project-images.model';

import { ProjectImagesService } from './project-images.service';

describe('Service Tests', () => {
  describe('ProjectImages Service', () => {
    let service: ProjectImagesService;
    let httpMock: HttpTestingController;
    let elemDefault: IProjectImages;
    let expectedResult: IProjectImages | IProjectImages[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProjectImagesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        imageContentType: 'image/png',
        image: 'AAAAAAA',
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

      it('should create a ProjectImages', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProjectImages()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProjectImages', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ProjectImages', () => {
        const patchObject = Object.assign(
          {
            image: 'BBBBBB',
          },
          new ProjectImages()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProjectImages', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            image: 'BBBBBB',
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

      it('should delete a ProjectImages', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProjectImagesToCollectionIfMissing', () => {
        it('should add a ProjectImages to an empty array', () => {
          const projectImages: IProjectImages = { id: 123 };
          expectedResult = service.addProjectImagesToCollectionIfMissing([], projectImages);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectImages);
        });

        it('should not add a ProjectImages to an array that contains it', () => {
          const projectImages: IProjectImages = { id: 123 };
          const projectImagesCollection: IProjectImages[] = [
            {
              ...projectImages,
            },
            { id: 456 },
          ];
          expectedResult = service.addProjectImagesToCollectionIfMissing(projectImagesCollection, projectImages);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ProjectImages to an array that doesn't contain it", () => {
          const projectImages: IProjectImages = { id: 123 };
          const projectImagesCollection: IProjectImages[] = [{ id: 456 }];
          expectedResult = service.addProjectImagesToCollectionIfMissing(projectImagesCollection, projectImages);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectImages);
        });

        it('should add only unique ProjectImages to an array', () => {
          const projectImagesArray: IProjectImages[] = [{ id: 123 }, { id: 456 }, { id: 76682 }];
          const projectImagesCollection: IProjectImages[] = [{ id: 123 }];
          expectedResult = service.addProjectImagesToCollectionIfMissing(projectImagesCollection, ...projectImagesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const projectImages: IProjectImages = { id: 123 };
          const projectImages2: IProjectImages = { id: 456 };
          expectedResult = service.addProjectImagesToCollectionIfMissing([], projectImages, projectImages2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectImages);
          expect(expectedResult).toContain(projectImages2);
        });

        it('should accept null and undefined values', () => {
          const projectImages: IProjectImages = { id: 123 };
          expectedResult = service.addProjectImagesToCollectionIfMissing([], null, projectImages, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectImages);
        });

        it('should return initial array if no ProjectImages is added', () => {
          const projectImagesCollection: IProjectImages[] = [{ id: 123 }];
          expectedResult = service.addProjectImagesToCollectionIfMissing(projectImagesCollection, undefined, null);
          expect(expectedResult).toEqual(projectImagesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
