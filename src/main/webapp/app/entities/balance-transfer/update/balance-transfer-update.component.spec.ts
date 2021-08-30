jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BalanceTransferService } from '../service/balance-transfer.service';
import { IBalanceTransfer, BalanceTransfer } from '../balance-transfer.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { BalanceTransferUpdateComponent } from './balance-transfer-update.component';

describe('Component Tests', () => {
  describe('BalanceTransfer Management Update Component', () => {
    let comp: BalanceTransferUpdateComponent;
    let fixture: ComponentFixture<BalanceTransferUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let balanceTransferService: BalanceTransferService;
    let userInfosService: UserInfosService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BalanceTransferUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BalanceTransferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BalanceTransferUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      balanceTransferService = TestBed.inject(BalanceTransferService);
      userInfosService = TestBed.inject(UserInfosService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userInfos query and add missing value', () => {
        const balanceTransfer: IBalanceTransfer = { id: 456 };
        const userInfos: IUserInfos = { id: 60562 };
        balanceTransfer.userInfos = userInfos;

        const userInfosCollection: IUserInfos[] = [{ id: 40136 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfosCollection })));
        const expectedCollection: IUserInfos[] = [userInfos, ...userInfosCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(userInfosCollection, userInfos);
        expect(comp.userInfosCollection).toEqual(expectedCollection);
      });

      it('Should call project query and add missing value', () => {
        const balanceTransfer: IBalanceTransfer = { id: 456 };
        const project: IProject = { id: 79217 };
        balanceTransfer.project = project;

        const projectCollection: IProject[] = [{ id: 62364 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const expectedCollection: IProject[] = [project, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, project);
        expect(comp.projectsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const balanceTransfer: IBalanceTransfer = { id: 456 };
        const userInfos: IUserInfos = { id: 24952 };
        balanceTransfer.userInfos = userInfos;
        const project: IProject = { id: 24184 };
        balanceTransfer.project = project;

        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(balanceTransfer));
        expect(comp.userInfosCollection).toContain(userInfos);
        expect(comp.projectsCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BalanceTransfer>>();
        const balanceTransfer = { id: 123 };
        jest.spyOn(balanceTransferService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: balanceTransfer }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(balanceTransferService.update).toHaveBeenCalledWith(balanceTransfer);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BalanceTransfer>>();
        const balanceTransfer = new BalanceTransfer();
        jest.spyOn(balanceTransferService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: balanceTransfer }));
        saveSubject.complete();

        // THEN
        expect(balanceTransferService.create).toHaveBeenCalledWith(balanceTransfer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BalanceTransfer>>();
        const balanceTransfer = { id: 123 };
        jest.spyOn(balanceTransferService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ balanceTransfer });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(balanceTransferService.update).toHaveBeenCalledWith(balanceTransfer);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserInfosById', () => {
        it('Should return tracked UserInfos primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserInfosById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProjectById', () => {
        it('Should return tracked Project primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProjectById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
