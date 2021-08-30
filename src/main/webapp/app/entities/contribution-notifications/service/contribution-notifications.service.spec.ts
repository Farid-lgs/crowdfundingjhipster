import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContributionNotifications, ContributionNotifications } from '../contribution-notifications.model';

import { ContributionNotificationsService } from './contribution-notifications.service';

describe('Service Tests', () => {
  describe('ContributionNotifications Service', () => {
    let service: ContributionNotificationsService;
    let httpMock: HttpTestingController;
    let elemDefault: IContributionNotifications;
    let expectedResult: IContributionNotifications | IContributionNotifications[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ContributionNotificationsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        content: 'AAAAAAA',
        sentAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ContributionNotifications', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.create(new ContributionNotifications()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ContributionNotifications', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            content: 'BBBBBB',
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ContributionNotifications', () => {
        const patchObject = Object.assign(
          {
            content: 'BBBBBB',
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new ContributionNotifications()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ContributionNotifications', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            content: 'BBBBBB',
            sentAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sentAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ContributionNotifications', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addContributionNotificationsToCollectionIfMissing', () => {
        it('should add a ContributionNotifications to an empty array', () => {
          const contributionNotifications: IContributionNotifications = { id: 123 };
          expectedResult = service.addContributionNotificationsToCollectionIfMissing([], contributionNotifications);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contributionNotifications);
        });

        it('should not add a ContributionNotifications to an array that contains it', () => {
          const contributionNotifications: IContributionNotifications = { id: 123 };
          const contributionNotificationsCollection: IContributionNotifications[] = [
            {
              ...contributionNotifications,
            },
            { id: 456 },
          ];
          expectedResult = service.addContributionNotificationsToCollectionIfMissing(
            contributionNotificationsCollection,
            contributionNotifications
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ContributionNotifications to an array that doesn't contain it", () => {
          const contributionNotifications: IContributionNotifications = { id: 123 };
          const contributionNotificationsCollection: IContributionNotifications[] = [{ id: 456 }];
          expectedResult = service.addContributionNotificationsToCollectionIfMissing(
            contributionNotificationsCollection,
            contributionNotifications
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contributionNotifications);
        });

        it('should add only unique ContributionNotifications to an array', () => {
          const contributionNotificationsArray: IContributionNotifications[] = [{ id: 123 }, { id: 456 }, { id: 20035 }];
          const contributionNotificationsCollection: IContributionNotifications[] = [{ id: 123 }];
          expectedResult = service.addContributionNotificationsToCollectionIfMissing(
            contributionNotificationsCollection,
            ...contributionNotificationsArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const contributionNotifications: IContributionNotifications = { id: 123 };
          const contributionNotifications2: IContributionNotifications = { id: 456 };
          expectedResult = service.addContributionNotificationsToCollectionIfMissing(
            [],
            contributionNotifications,
            contributionNotifications2
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(contributionNotifications);
          expect(expectedResult).toContain(contributionNotifications2);
        });

        it('should accept null and undefined values', () => {
          const contributionNotifications: IContributionNotifications = { id: 123 };
          expectedResult = service.addContributionNotificationsToCollectionIfMissing([], null, contributionNotifications, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(contributionNotifications);
        });

        it('should return initial array if no ContributionNotifications is added', () => {
          const contributionNotificationsCollection: IContributionNotifications[] = [{ id: 123 }];
          expectedResult = service.addContributionNotificationsToCollectionIfMissing(contributionNotificationsCollection, undefined, null);
          expect(expectedResult).toEqual(contributionNotificationsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
