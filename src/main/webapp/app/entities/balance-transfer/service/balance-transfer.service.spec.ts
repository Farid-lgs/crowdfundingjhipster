import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBalanceTransfer, BalanceTransfer } from '../balance-transfer.model';

import { BalanceTransferService } from './balance-transfer.service';

describe('Service Tests', () => {
  describe('BalanceTransfer Service', () => {
    let service: BalanceTransferService;
    let httpMock: HttpTestingController;
    let elemDefault: IBalanceTransfer;
    let expectedResult: IBalanceTransfer | IBalanceTransfer[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BalanceTransferService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        amount: 0,
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

      it('should create a BalanceTransfer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BalanceTransfer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BalanceTransfer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a BalanceTransfer', () => {
        const patchObject = Object.assign({}, new BalanceTransfer());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BalanceTransfer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            amount: 1,
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

      it('should delete a BalanceTransfer', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBalanceTransferToCollectionIfMissing', () => {
        it('should add a BalanceTransfer to an empty array', () => {
          const balanceTransfer: IBalanceTransfer = { id: 123 };
          expectedResult = service.addBalanceTransferToCollectionIfMissing([], balanceTransfer);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(balanceTransfer);
        });

        it('should not add a BalanceTransfer to an array that contains it', () => {
          const balanceTransfer: IBalanceTransfer = { id: 123 };
          const balanceTransferCollection: IBalanceTransfer[] = [
            {
              ...balanceTransfer,
            },
            { id: 456 },
          ];
          expectedResult = service.addBalanceTransferToCollectionIfMissing(balanceTransferCollection, balanceTransfer);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BalanceTransfer to an array that doesn't contain it", () => {
          const balanceTransfer: IBalanceTransfer = { id: 123 };
          const balanceTransferCollection: IBalanceTransfer[] = [{ id: 456 }];
          expectedResult = service.addBalanceTransferToCollectionIfMissing(balanceTransferCollection, balanceTransfer);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(balanceTransfer);
        });

        it('should add only unique BalanceTransfer to an array', () => {
          const balanceTransferArray: IBalanceTransfer[] = [{ id: 123 }, { id: 456 }, { id: 92953 }];
          const balanceTransferCollection: IBalanceTransfer[] = [{ id: 123 }];
          expectedResult = service.addBalanceTransferToCollectionIfMissing(balanceTransferCollection, ...balanceTransferArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const balanceTransfer: IBalanceTransfer = { id: 123 };
          const balanceTransfer2: IBalanceTransfer = { id: 456 };
          expectedResult = service.addBalanceTransferToCollectionIfMissing([], balanceTransfer, balanceTransfer2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(balanceTransfer);
          expect(expectedResult).toContain(balanceTransfer2);
        });

        it('should accept null and undefined values', () => {
          const balanceTransfer: IBalanceTransfer = { id: 123 };
          expectedResult = service.addBalanceTransferToCollectionIfMissing([], null, balanceTransfer, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(balanceTransfer);
        });

        it('should return initial array if no BalanceTransfer is added', () => {
          const balanceTransferCollection: IBalanceTransfer[] = [{ id: 123 }];
          expectedResult = service.addBalanceTransferToCollectionIfMissing(balanceTransferCollection, undefined, null);
          expect(expectedResult).toEqual(balanceTransferCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
