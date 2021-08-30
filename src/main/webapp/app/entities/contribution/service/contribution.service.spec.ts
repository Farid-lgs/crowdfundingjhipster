import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContribution, Contribution } from '../contribution.model';

import { ContributionService } from './contribution.service';

describe('Service Tests', () => {
  describe('Contribution Service', () => {
    let service: ContributionService;
    let httpMock: HttpTestingController;
    let elemDefault: IContribution;
    let expectedResult: IContribution | IContribution[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ContributionService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        amount: 0,
        payerName: 'AAAAAAA',
        createdAt: currentDate,
        updatedAt: currentDate,
        anonymous: false,
        rewarded: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Contribution', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Contribution()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contribution', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            amount: 1,
            payerName: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            anonymous: true,
            rewarded: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Contribution', () => {
        const patchObject = Object.assign(
          {
            amount: 1,
            payerName: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            anonymous: true,
          },
          new Contribution()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Contribution', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            amount: 1,
            payerName: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
            anonymous: true,
            rewarded: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Contribution', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addContributionToCollectionIfMissing', () => {
        it('should add a Contribution to an empty array', () => {
          const contribution: IContribution = { id: 123 };
          expectedResult = service.addContributionToCollectionIfMissing([], contribution);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contribution);
        });

        it('should not add a Contribution to an array that contains it', () => {
          const contribution: IContribution = { id: 123 };
          const contributionCollection: IContribution[] = [
            {
              ...contribution,
            },
            { id: 456 },
          ];
          expectedResult = service.addContributionToCollectionIfMissing(contributionCollection, contribution);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Contribution to an array that doesn't contain it", () => {
          const contribution: IContribution = { id: 123 };
          const contributionCollection: IContribution[] = [{ id: 456 }];
          expectedResult = service.addContributionToCollectionIfMissing(contributionCollection, contribution);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contribution);
        });

        it('should add only unique Contribution to an array', () => {
          const contributionArray: IContribution[] = [{ id: 123 }, { id: 456 }, { id: 48947 }];
          const contributionCollection: IContribution[] = [{ id: 123 }];
          expectedResult = service.addContributionToCollectionIfMissing(contributionCollection, ...contributionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const contribution: IContribution = { id: 123 };
          const contribution2: IContribution = { id: 456 };
          expectedResult = service.addContributionToCollectionIfMissing([], contribution, contribution2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contribution);
          expect(expectedResult).toContain(contribution2);
        });

        it('should accept null and undefined values', () => {
          const contribution: IContribution = { id: 123 };
          expectedResult = service.addContributionToCollectionIfMissing([], null, contribution, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contribution);
        });

        it('should return initial array if no Contribution is added', () => {
          const contributionCollection: IContribution[] = [{ id: 123 }];
          expectedResult = service.addContributionToCollectionIfMissing(contributionCollection, undefined, null);
          expect(expectedResult).toEqual(contributionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
