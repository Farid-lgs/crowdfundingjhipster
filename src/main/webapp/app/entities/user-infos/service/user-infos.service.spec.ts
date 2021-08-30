import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IUserInfos, UserInfos } from '../user-infos.model';

import { UserInfosService } from './user-infos.service';

describe('Service Tests', () => {
  describe('UserInfos Service', () => {
    let service: UserInfosService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserInfos;
    let expectedResult: IUserInfos | IUserInfos[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(UserInfosService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        publicName: 'AAAAAAA',
        birthDate: currentDate,
        twitter: 'AAAAAAA',
        facebook: 'AAAAAAA',
        linkedIn: 'AAAAAAA',
        description: 'AAAAAAA',
        coverImageContentType: 'image/png',
        coverImage: 'AAAAAAA',
        commonId: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.create(new UserInfos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            publicName: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            twitter: 'BBBBBB',
            facebook: 'BBBBBB',
            linkedIn: 'BBBBBB',
            description: 'BBBBBB',
            coverImage: 'BBBBBB',
            commonId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a UserInfos', () => {
        const patchObject = Object.assign(
          {
            publicName: 'BBBBBB',
            commonId: 'BBBBBB',
          },
          new UserInfos()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            publicName: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            twitter: 'BBBBBB',
            facebook: 'BBBBBB',
            linkedIn: 'BBBBBB',
            description: 'BBBBBB',
            coverImage: 'BBBBBB',
            commonId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserInfos', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addUserInfosToCollectionIfMissing', () => {
        it('should add a UserInfos to an empty array', () => {
          const userInfos: IUserInfos = { id: 123 };
          expectedResult = service.addUserInfosToCollectionIfMissing([], userInfos);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(userInfos);
        });

        it('should not add a UserInfos to an array that contains it', () => {
          const userInfos: IUserInfos = { id: 123 };
          const userInfosCollection: IUserInfos[] = [
            {
              ...userInfos,
            },
            { id: 456 },
          ];
          expectedResult = service.addUserInfosToCollectionIfMissing(userInfosCollection, userInfos);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a UserInfos to an array that doesn't contain it", () => {
          const userInfos: IUserInfos = { id: 123 };
          const userInfosCollection: IUserInfos[] = [{ id: 456 }];
          expectedResult = service.addUserInfosToCollectionIfMissing(userInfosCollection, userInfos);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(userInfos);
        });

        it('should add only unique UserInfos to an array', () => {
          const userInfosArray: IUserInfos[] = [{ id: 123 }, { id: 456 }, { id: 3567 }];
          const userInfosCollection: IUserInfos[] = [{ id: 123 }];
          expectedResult = service.addUserInfosToCollectionIfMissing(userInfosCollection, ...userInfosArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const userInfos: IUserInfos = { id: 123 };
          const userInfos2: IUserInfos = { id: 456 };
          expectedResult = service.addUserInfosToCollectionIfMissing([], userInfos, userInfos2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(userInfos);
          expect(expectedResult).toContain(userInfos2);
        });

        it('should accept null and undefined values', () => {
          const userInfos: IUserInfos = { id: 123 };
          expectedResult = service.addUserInfosToCollectionIfMissing([], null, userInfos, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(userInfos);
        });

        it('should return initial array if no UserInfos is added', () => {
          const userInfosCollection: IUserInfos[] = [{ id: 123 }];
          expectedResult = service.addUserInfosToCollectionIfMissing(userInfosCollection, undefined, null);
          expect(expectedResult).toEqual(userInfosCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
