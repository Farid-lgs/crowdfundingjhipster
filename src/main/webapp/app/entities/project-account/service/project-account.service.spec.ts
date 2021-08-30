import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectAccount, ProjectAccount } from '../project-account.model';

import { ProjectAccountService } from './project-account.service';

describe('Service Tests', () => {
  describe('ProjectAccount Service', () => {
    let service: ProjectAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IProjectAccount;
    let expectedResult: IProjectAccount | IProjectAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProjectAccountService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        balance: 'AAAAAAA',
        number: 0,
        bank: 'AAAAAAA',
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

      it('should create a ProjectAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProjectAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProjectAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            balance: 'BBBBBB',
            number: 1,
            bank: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ProjectAccount', () => {
        const patchObject = Object.assign(
          {
            number: 1,
          },
          new ProjectAccount()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProjectAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            balance: 'BBBBBB',
            number: 1,
            bank: 'BBBBBB',
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

      it('should delete a ProjectAccount', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProjectAccountToCollectionIfMissing', () => {
        it('should add a ProjectAccount to an empty array', () => {
          const projectAccount: IProjectAccount = { id: 123 };
          expectedResult = service.addProjectAccountToCollectionIfMissing([], projectAccount);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectAccount);
        });

        it('should not add a ProjectAccount to an array that contains it', () => {
          const projectAccount: IProjectAccount = { id: 123 };
          const projectAccountCollection: IProjectAccount[] = [
            {
              ...projectAccount,
            },
            { id: 456 },
          ];
          expectedResult = service.addProjectAccountToCollectionIfMissing(projectAccountCollection, projectAccount);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ProjectAccount to an array that doesn't contain it", () => {
          const projectAccount: IProjectAccount = { id: 123 };
          const projectAccountCollection: IProjectAccount[] = [{ id: 456 }];
          expectedResult = service.addProjectAccountToCollectionIfMissing(projectAccountCollection, projectAccount);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectAccount);
        });

        it('should add only unique ProjectAccount to an array', () => {
          const projectAccountArray: IProjectAccount[] = [{ id: 123 }, { id: 456 }, { id: 67086 }];
          const projectAccountCollection: IProjectAccount[] = [{ id: 123 }];
          expectedResult = service.addProjectAccountToCollectionIfMissing(projectAccountCollection, ...projectAccountArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const projectAccount: IProjectAccount = { id: 123 };
          const projectAccount2: IProjectAccount = { id: 456 };
          expectedResult = service.addProjectAccountToCollectionIfMissing([], projectAccount, projectAccount2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(projectAccount);
          expect(expectedResult).toContain(projectAccount2);
        });

        it('should accept null and undefined values', () => {
          const projectAccount: IProjectAccount = { id: 123 };
          expectedResult = service.addProjectAccountToCollectionIfMissing([], null, projectAccount, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(projectAccount);
        });

        it('should return initial array if no ProjectAccount is added', () => {
          const projectAccountCollection: IProjectAccount[] = [{ id: 123 }];
          expectedResult = service.addProjectAccountToCollectionIfMissing(projectAccountCollection, undefined, null);
          expect(expectedResult).toEqual(projectAccountCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
