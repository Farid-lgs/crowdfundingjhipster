jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RewardService } from '../service/reward.service';
import { IReward, Reward } from '../reward.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

import { RewardUpdateComponent } from './reward-update.component';

describe('Component Tests', () => {
  describe('Reward Management Update Component', () => {
    let comp: RewardUpdateComponent;
    let fixture: ComponentFixture<RewardUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rewardService: RewardService;
    let projectService: ProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RewardUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RewardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RewardUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rewardService = TestBed.inject(RewardService);
      projectService = TestBed.inject(ProjectService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Project query and add missing value', () => {
        const reward: IReward = { id: 456 };
        const project: IProject = { id: 95225 };
        reward.project = project;

        const projectCollection: IProject[] = [{ id: 70972 }];
        jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
        const additionalProjects = [project];
        const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
        jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ reward });
        comp.ngOnInit();

        expect(projectService.query).toHaveBeenCalled();
        expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(projectCollection, ...additionalProjects);
        expect(comp.projectsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const reward: IReward = { id: 456 };
        const project: IProject = { id: 60785 };
        reward.project = project;

        activatedRoute.data = of({ reward });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(reward));
        expect(comp.projectsSharedCollection).toContain(project);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reward>>();
        const reward = { id: 123 };
        jest.spyOn(rewardService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reward });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reward }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rewardService.update).toHaveBeenCalledWith(reward);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reward>>();
        const reward = new Reward();
        jest.spyOn(rewardService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reward });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reward }));
        saveSubject.complete();

        // THEN
        expect(rewardService.create).toHaveBeenCalledWith(reward);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reward>>();
        const reward = { id: 123 };
        jest.spyOn(rewardService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reward });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rewardService.update).toHaveBeenCalledWith(reward);
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
