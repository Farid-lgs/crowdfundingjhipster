jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProjectAccountService } from '../service/project-account.service';
import { IProjectAccount, ProjectAccount } from '../project-account.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { ProjectAccountUpdateComponent } from './project-account-update.component';

describe('Component Tests', () => {
  describe('ProjectAccount Management Update Component', () => {
    let comp: ProjectAccountUpdateComponent;
    let fixture: ComponentFixture<ProjectAccountUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let projectAccountService: ProjectAccountService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectAccountUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProjectAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectAccountUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      projectAccountService = TestBed.inject(ProjectAccountService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Project query and add missing value', () => {
        const projectAccount: IProjectAccount = { id: 456 };
        const project: IProject = { id: 1272 };
        projectAccount.project = project;

        const projectCollection: IProject[] = [{ id: 75166 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const additionalProjects = [project];
        const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ projectAccount });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
        expect(comp.projectsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const projectAccount: IProjectAccount = { id: 456 };
        const project: IProject = { id: 508 };
        projectAccount.project = project;

        activatedRoute.data = of({ projectAccount });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(projectAccount));
        expect(comp.projectsSharedCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectAccount>>();
        const projectAccount = { id: 123 };
        jest.spyOn(projectAccountService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectAccount }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(projectAccountService.update).toHaveBeenCalledWith(projectAccount);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectAccount>>();
        const projectAccount = new ProjectAccount();
        jest.spyOn(projectAccountService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectAccount }));
        saveSubject.complete();

        // THEN
        expect(projectAccountService.create).toHaveBeenCalledWith(projectAccount);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectAccount>>();
        const projectAccount = { id: 123 };
        jest.spyOn(projectAccountService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectAccount });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(projectAccountService.update).toHaveBeenCalledWith(projectAccount);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
