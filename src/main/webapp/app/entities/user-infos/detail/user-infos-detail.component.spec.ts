import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataUtils } from 'app/core/util/data-util.service';

import { UserInfosDetailComponent } from './user-infos-detail.component';

describe('Component Tests', () => {
  describe('UserInfos Management Detail Component', () => {
    let comp: UserInfosDetailComponent;
    let fixture: ComponentFixture<UserInfosDetailComponent>;
    let dataUtils: DataUtils;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [UserInfosDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ userInfos: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(UserInfosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserInfosDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = TestBed.inject(DataUtils);
      jest.spyOn(window, 'open').mockImplementation(() => null);
    });

    describe('OnInit', () => {
      it('Should load userInfos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userInfos).toEqual(expect.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from DataUtils', () => {
        // GIVEN
        jest.spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from DataUtils', () => {
        // GIVEN
        jest.spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeBase64, fakeContentType);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeBase64, fakeContentType);
      });
    });
  });
});
