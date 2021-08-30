jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProjectCommentService } from '../service/project-comment.service';
import { IProjectComment, ProjectComment } from '../project-comment.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { ProjectCommentUpdateComponent } from './project-comment-update.component';

describe('Component Tests', () => {
  describe('ProjectComment Management Update Component', () => {
    let comp: ProjectCommentUpdateComponent;
    let fixture: ComponentFixture<ProjectCommentUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let projectCommentService: ProjectCommentService;
    let userInfosService: UserInfosService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectCommentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProjectCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectCommentUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      projectCommentService = TestBed.inject(ProjectCommentService);
      userInfosService = TestBed.inject(UserInfosService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call UserInfos query and add missing value', () => {
        const projectComment: IProjectComment = { id: 456 };
        const userInfos: IUserInfos = { id: 88944 };
        projectComment.userInfos = userInfos;

        const userInfosCollection: IUserInfos[] = [{ id: 68320 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfosCollection })));
        const additionalUserInfos = [userInfos];
        const expectedCollection: IUserInfos[] = [...additionalUserInfos, ...userInfosCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(userInfosCollection, ...additionalUserInfos);
        expect(comp.userInfosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Project query and add missing value', () => {
        const projectComment: IProjectComment = { id: 456 };
        const project: IProject = { id: 8790 };
        projectComment.project = project;

        const projectCollection: IProject[] = [{ id: 44896 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const additionalProjects = [project];
        const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
        expect(comp.projectsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const projectComment: IProjectComment = { id: 456 };
        const userInfos: IUserInfos = { id: 41677 };
        projectComment.userInfos = userInfos;
        const project: IProject = { id: 65691 };
        projectComment.project = project;

        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(projectComment));
        expect(comp.userInfosSharedCollection).toContain(userInfos);
        expect(comp.projectsSharedCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectComment>>();
        const projectComment = { id: 123 };
        jest.spyOn(projectCommentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectComment }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(projectCommentService.update).toHaveBeenCalledWith(projectComment);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectComment>>();
        const projectComment = new ProjectComment();
        jest.spyOn(projectCommentService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectComment }));
        saveSubject.complete();

        // THEN
        expect(projectCommentService.create).toHaveBeenCalledWith(projectComment);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectComment>>();
        const projectComment = { id: 123 };
        jest.spyOn(projectCommentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectComment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(projectCommentService.update).toHaveBeenCalledWith(projectComment);
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
