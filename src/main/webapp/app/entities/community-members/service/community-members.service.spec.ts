import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICommunityMembers, CommunityMembers } from '../community-members.model';

import { CommunityMembersService } from './community-members.service';

describe('Service Tests', () => {
  describe('CommunityMembers Service', () => {
    let service: CommunityMembersService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommunityMembers;
    let expectedResult: ICommunityMembers | ICommunityMembers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CommunityMembersService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a CommunityMembers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CommunityMembers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommunityMembers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CommunityMembers', () => {
        const patchObject = Object.assign({}, new CommunityMembers());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommunityMembers', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a CommunityMembers', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCommunityMembersToCollectionIfMissing', () => {
        it('should add a CommunityMembers to an empty array', () => {
          const communityMembers: ICommunityMembers = { id: 123 };
          expectedResult = service.addCommunityMembersToCollectionIfMissing([], communityMembers);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(communityMembers);
        });

        it('should not add a CommunityMembers to an array that contains it', () => {
          const communityMembers: ICommunityMembers = { id: 123 };
          const communityMembersCollection: ICommunityMembers[] = [
            {
              ...communityMembers,
            },
            { id: 456 },
          ];
          expectedResult = service.addCommunityMembersToCollectionIfMissing(communityMembersCollection, communityMembers);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CommunityMembers to an array that doesn't contain it", () => {
          const communityMembers: ICommunityMembers = { id: 123 };
          const communityMembersCollection: ICommunityMembers[] = [{ id: 456 }];
          expectedResult = service.addCommunityMembersToCollectionIfMissing(communityMembersCollection, communityMembers);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(communityMembers);
        });

        it('should add only unique CommunityMembers to an array', () => {
          const communityMembersArray: ICommunityMembers[] = [{ id: 123 }, { id: 456 }, { id: 55087 }];
          const communityMembersCollection: ICommunityMembers[] = [{ id: 123 }];
          expectedResult = service.addCommunityMembersToCollectionIfMissing(communityMembersCollection, ...communityMembersArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const communityMembers: ICommunityMembers = { id: 123 };
          const communityMembers2: ICommunityMembers = { id: 456 };
          expectedResult = service.addCommunityMembersToCollectionIfMissing([], communityMembers, communityMembers2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(communityMembers);
          expect(expectedResult).toContain(communityMembers2);
        });

        it('should accept null and undefined values', () => {
          const communityMembers: ICommunityMembers = { id: 123 };
          expectedResult = service.addCommunityMembersToCollectionIfMissing([], null, communityMembers, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(communityMembers);
        });

        it('should return initial array if no CommunityMembers is added', () => {
          const communityMembersCollection: ICommunityMembers[] = [{ id: 123 }];
          expectedResult = service.addCommunityMembersToCollectionIfMissing(communityMembersCollection, undefined, null);
          expect(expectedResult).toEqual(communityMembersCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
