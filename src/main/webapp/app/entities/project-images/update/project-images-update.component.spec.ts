jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProjectImagesService } from '../service/project-images.service';
import { IProjectImages, ProjectImages } from '../project-images.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { ProjectImagesUpdateComponent } from './project-images-update.component';

describe('Component Tests', () => {
  describe('ProjectImages Management Update Component', () => {
    let comp: ProjectImagesUpdateComponent;
    let fixture: ComponentFixture<ProjectImagesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let projectImagesService: ProjectImagesService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectImagesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProjectImagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectImagesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      projectImagesService = TestBed.inject(ProjectImagesService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Project query and add missing value', () => {
        const projectImages: IProjectImages = { id: 456 };
        const project: IProject = { id: 27737 };
        projectImages.project = project;

        const projectCollection: IProject[] = [{ id: 27346 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const additionalProjects = [project];
        const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ projectImages });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
        expect(comp.projectsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const projectImages: IProjectImages = { id: 456 };
        const project: IProject = { id: 95134 };
        projectImages.project = project;

        activatedRoute.data = of({ projectImages });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(projectImages));
        expect(comp.projectsSharedCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectImages>>();
        const projectImages = { id: 123 };
        jest.spyOn(projectImagesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectImages });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectImages }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(projectImagesService.update).toHaveBeenCalledWith(projectImages);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectImages>>();
        const projectImages = new ProjectImages();
        jest.spyOn(projectImagesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectImages });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectImages }));
        saveSubject.complete();

        // THEN
        expect(projectImagesService.create).toHaveBeenCalledWith(projectImages);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectImages>>();
        const projectImages = { id: 123 };
        jest.spyOn(projectImagesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectImages });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(projectImagesService.update).toHaveBeenCalledWith(projectImages);
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
