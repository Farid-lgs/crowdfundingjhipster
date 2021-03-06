import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReward, Reward } from '../reward.model';

import { RewardService } from './reward.service';

describe('Service Tests', () => {
  describe('Reward Service', () => {
    let service: RewardService;
    let httpMock: HttpTestingController;
    let elemDefault: IReward;
    let expectedResult: IReward | IReward[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RewardService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        title: 'AAAAAAA',
        description: 'AAAAAAA',
        minimumValue: 0,
        deliverAt: currentDate,
        expiresAt: currentDate,
        maximumContributions: 0,
        imageContentType: 'image/png',
        image: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deliverAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reward', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deliverAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliverAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Reward()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reward', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            description: 'BBBBBB',
            minimumValue: 1,
            deliverAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
            maximumContributions: 1,
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliverAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Reward', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            minimumValue: 1,
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
            maximumContributions: 1,
          },
          new Reward()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            deliverAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reward', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            title: 'BBBBBB',
            description: 'BBBBBB',
            minimumValue: 1,
            deliverAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
            maximumContributions: 1,
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deliverAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reward', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRewardToCollectionIfMissing', () => {
        it('should add a Reward to an empty array', () => {
          const reward: IReward = { id: 123 };
          expectedResult = service.addRewardToCollectionIfMissing([], reward);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reward);
        });

        it('should not add a Reward to an array that contains it', () => {
          const reward: IReward = { id: 123 };
          const rewardCollection: IReward[] = [
            {
              ...reward,
            },
            { id: 456 },
          ];
          expectedResult = service.addRewardToCollectionIfMissing(rewardCollection, reward);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Reward to an array that doesn't contain it", () => {
          const reward: IReward = { id: 123 };
          const rewardCollection: IReward[] = [{ id: 456 }];
          expectedResult = service.addRewardToCollectionIfMissing(rewardCollection, reward);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reward);
        });

        it('should add only unique Reward to an array', () => {
          const rewardArray: IReward[] = [{ id: 123 }, { id: 456 }, { id: 21172 }];
          const rewardCollection: IReward[] = [{ id: 123 }];
          expectedResult = service.addRewardToCollectionIfMissing(rewardCollection, ...rewardArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const reward: IReward = { id: 123 };
          const reward2: IReward = { id: 456 };
          expectedResult = service.addRewardToCollectionIfMissing([], reward, reward2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reward);
          expect(expectedResult).toContain(reward2);
        });

        it('should accept null and undefined values', () => {
          const reward: IReward = { id: 123 };
          expectedResult = service.addRewardToCollectionIfMissing([], null, reward, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reward);
        });

        it('should return initial array if no Reward is added', () => {
          const rewardCollection: IReward[] = [{ id: 123 }];
          expectedResult = service.addRewardToCollectionIfMissing(rewardCollection, undefined, null);
          expect(expectedResult).toEqual(rewardCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
