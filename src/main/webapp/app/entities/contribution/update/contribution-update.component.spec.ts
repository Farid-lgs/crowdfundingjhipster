jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContributionService } from '../service/contribution.service';
import { IContribution, Contribution } from '../contribution.model';
import { IContributionNotifications } from 'app/entities/contribution-notifications/contribution-notifications.model';
import { ContributionNotificationsService } from 'app/entities/contribution-notifications/service/contribution-notifications.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { ContributionUpdateComponent } from './contribution-update.component';

describe('Component Tests', () => {
  describe('Contribution Management Update Component', () => {
    let comp: ContributionUpdateComponent;
    let fixture: ComponentFixture<ContributionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let contributionService: ContributionService;
    let contributionNotificationsService: ContributionNotificationsService;
    let userInfosService: UserInfosService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ContributionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ContributionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContributionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      contributionService = TestBed.inject(ContributionService);
      contributionNotificationsService = TestBed.inject(ContributionNotificationsService);
      userInfosService = TestBed.inject(UserInfosService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call contributionNotifications query and add missing value', () => {
        const contribution: IContribution = { id: 456 };
        const contributionNotifications: IContributionNotifications = { id: 12065 };
        contribution.contributionNotifications = contributionNotifications;

        const contributionNotificationsCollection: IContributionNotifications[] = [{ id: 83129 }];
        jest
          .spyOn(contributionNotificationsService, 'query')
          .mockReturnValue(of(new HttpResponse({ body: contributionNotificationsCollection })));
        const expectedCollection: IContributionNotifications[] = [contributionNotifications, ...contributionNotificationsCollection];
        jest
          .spyOn(contributionNotificationsService, 'addContributionNotificationsToCollectionIfMissing')
          .mockReturnValue(expectedCollection);

        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        expect(contributionNotificationsService.query).toHaveBeenCalled();
        expect(contributionNotificationsService.addContributionNotificationsToCollectionIfMissing).toHaveBeenCalledWith(
          contributionNotificationsCollection,
          contributionNotifications
        );
        expect(comp.contributionNotificationsCollection).toEqual(expectedCollection);
      });

      it('Should call UserInfos query and add missing value', () => {
        const contribution: IContribution = { id: 456 };
        const userInfos: IUserInfos = { id: 53206 };
        contribution.userInfos = userInfos;

        const userInfosCollection: IUserInfos[] = [{ id: 29133 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfosCollection })));
        const additionalUserInfos = [userInfos];
        const expectedCollection: IUserInfos[] = [...additionalUserInfos, ...userInfosCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(userInfosCollection, ...additionalUserInfos);
        expect(comp.userInfosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Project query and add missing value', () => {
        const contribution: IContribution = { id: 456 };
        const project: IProject = { id: 56669 };
        contribution.project = project;

        const projectCollection: IProject[] = [{ id: 35134 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const additionalProjects = [project];
        const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
        expect(comp.projectsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const contribution: IContribution = { id: 456 };
        const contributionNotifications: IContributionNotifications = { id: 41736 };
        contribution.contributionNotifications = contributionNotifications;
        const userInfos: IUserInfos = { id: 85474 };
        contribution.userInfos = userInfos;
        const project: IProject = { id: 84229 };
        contribution.project = project;

        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(contribution));
        expect(comp.contributionNotificationsCollection).toContain(contributionNotifications);
        expect(comp.userInfosSharedCollection).toContain(userInfos);
        expect(comp.projectsSharedCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contribution>>();
        const contribution = { id: 123 };
        jest.spyOn(contributionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contribution }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(contributionService.update).toHaveBeenCalledWith(contribution);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contribution>>();
        const contribution = new Contribution();
        jest.spyOn(contributionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contribution }));
        saveSubject.complete();

        // THEN
        expect(contributionService.create).toHaveBeenCalledWith(contribution);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contribution>>();
        const contribution = { id: 123 };
        jest.spyOn(contributionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contribution });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(contributionService.update).toHaveBeenCalledWith(contribution);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackContributionNotificationsById', () => {
        it('Should return tracked ContributionNotifications primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackContributionNotificationsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
