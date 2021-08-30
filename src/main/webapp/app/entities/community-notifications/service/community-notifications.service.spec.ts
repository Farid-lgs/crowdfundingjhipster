import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommunityNotifications, CommunityNotifications } from '../community-notifications.model';

import { CommunityNotificationsService } from './community-notifications.service';

describe('Service Tests', () => {
  describe('CommunityNotifications Service', () => {
    let service: CommunityNotificationsService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommunityNotifications;
    let expectedResult: ICommunityNotifications | ICommunityNotifications[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CommunityNotificationsService);
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

      it('should create a CommunityNotifications', () => {
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

        service.create(new CommunityNotifications()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommunityNotifications', () => {
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

      it('should partial update a CommunityNotifications', () => {
        const patchObject = Object.assign({}, new CommunityNotifications());

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

      it('should return a list of CommunityNotifications', () => {
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

      it('should delete a CommunityNotifications', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCommunityNotificationsToCollectionIfMissing', () => {
        it('should add a CommunityNotifications to an empty array', () => {
          const communityNotifications: ICommunityNotifications = { id: 123 };
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing([], communityNotifications);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(communityNotifications);
        });

        it('should not add a CommunityNotifications to an array that contains it', () => {
          const communityNotifications: ICommunityNotifications = { id: 123 };
          const communityNotificationsCollection: ICommunityNotifications[] = [
            {
              ...communityNotifications,
            },
            { id: 456 },
          ];
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing(communityNotificationsCollection, communityNotifications);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CommunityNotifications to an array that doesn't contain it", () => {
          const communityNotifications: ICommunityNotifications = { id: 123 };
          const communityNotificationsCollection: ICommunityNotifications[] = [{ id: 456 }];
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing(communityNotificationsCollection, communityNotifications);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(communityNotifications);
        });

        it('should add only unique CommunityNotifications to an array', () => {
          const communityNotificationsArray: ICommunityNotifications[] = [{ id: 123 }, { id: 456 }, { id: 76350 }];
          const communityNotificationsCollection: ICommunityNotifications[] = [{ id: 123 }];
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing(
            communityNotificationsCollection,
            ...communityNotificationsArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const communityNotifications: ICommunityNotifications = { id: 123 };
          const communityNotifications2: ICommunityNotifications = { id: 456 };
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing([], communityNotifications, communityNotifications2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(communityNotifications);
          expect(expectedResult).toContain(communityNotifications2);
        });

        it('should accept null and undefined values', () => {
          const communityNotifications: ICommunityNotifications = { id: 123 };
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing([], null, communityNotifications, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(communityNotifications);
        });

        it('should return initial array if no CommunityNotifications is added', () => {
          const communityNotificationsCollection: ICommunityNotifications[] = [{ id: 123 }];
          expectedResult = service.addCommunityNotificationsToCollectionIfMissing(communityNotificationsCollection, undefined, null);
          expect(expectedResult).toEqual(communityNotificationsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
