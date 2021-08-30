jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProjectService } from '../service/project.service';
import { IProject, Project } from '../project.model';
import { ICommunity } from 'app/entities/community/community.model';
import { CommunityService } from 'app/entities/community/service/community.service';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { UserInfosService } from 'app/entities/user-infos/service/user-infos.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Component Tests', () => {
  describe('Project Management Update Component', () => {
    let comp: ProjectUpdateComponent;
    let fixture: ComponentFixture<ProjectUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let projectService: ProjectService;
    let communityService: CommunityService;
    let userInfosService: UserInfosService;
    let categoryService: CategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProjectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      projectService = TestBed.inject(ProjectService);
      communityService = TestBed.inject(CommunityService);
      userInfosService = TestBed.inject(UserInfosService);
      categoryService = TestBed.inject(CategoryService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Community query and add missing value', () => {
        const project: IProject = { id: 456 };
        const community: ICommunity = { id: 64458 };
        project.community = community;

        const communityCollection: ICommunity[] = [{ id: 75119 }];
        jest.spyOn(communityService, 'query').mockReturnValue(of(new HttpResponse({ body: communityCollection })));
        const additionalCommunities = [community];
        const expectedCollection: ICommunity[] = [...additionalCommunities, ...communityCollection];
        jest.spyOn(communityService, 'addCommunityToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ project });
        comp.ngOnInit();

        expect(communityService.query).toHaveBeenCalled();
        expect(communityService.addCommunityToCollectionIfMissing).toHaveBeenCalledWith(communityCollection, ...additionalCommunities);
        expect(comp.communitiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call UserInfos query and add missing value', () => {
        const project: IProject = { id: 456 };
        const userInfos: IUserInfos = { id: 56342 };
        project.userInfos = userInfos;

        const userInfosCollection: IUserInfos[] = [{ id: 98841 }];
        jest.spyOn(userInfosService, 'query').mockReturnValue(of(new HttpResponse({ body: userInfosCollection })));
        const additionalUserInfos = [userInfos];
        const expectedCollection: IUserInfos[] = [...additionalUserInfos, ...userInfosCollection];
        jest.spyOn(userInfosService, 'addUserInfosToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ project });
        comp.ngOnInit();

        expect(userInfosService.query).toHaveBeenCalled();
        expect(userInfosService.addUserInfosToCollectionIfMissing).toHaveBeenCalledWith(userInfosCollection, ...additionalUserInfos);
        expect(comp.userInfosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Category query and add missing value', () => {
        const project: IProject = { id: 456 };
        const category: ICategory = { id: 88917 };
        project.category = category;

        const categoryCollection: ICategory[] = [{ id: 87348 }];
        jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
        const additionalCategories = [category];
        const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
        jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ project });
        comp.ngOnInit();

        expect(categoryService.query).toHaveBeenCalled();
        expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(categoryCollection, ...additionalCategories);
        expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const project: IProject = { id: 456 };
        const community: ICommunity = { id: 23423 };
        project.community = community;
        const userInfos: IUserInfos = { id: 75694 };
        project.userInfos = userInfos;
        const category: ICategory = { id: 57076 };
        project.category = category;

        activatedRoute.data = of({ project });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(project));
        expect(comp.communitiesSharedCollection).toContain(community);
        expect(comp.userInfosSharedCollection).toContain(userInfos);
        expect(comp.categoriesSharedCollection).toContain(category);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Project>>();
        const project = { id: 123 };
        jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ project });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: project }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(projectService.update).toHaveBeenCalledWith(project);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Project>>();
        const project = new Project();
        jest.spyOn(projectService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ project });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: project }));
        saveSubject.complete();

        // THEN
        expect(projectService.create).toHaveBeenCalledWith(project);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Project>>();
        const project = { id: 123 };
        jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ project });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(projectService.update).toHaveBeenCalledWith(project);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCommunityById', () => {
        it('Should return tracked Community primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommunityById(0, entity);
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

      describe('trackCategoryById', () => {
        it('Should return tracked Category primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategoryById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
