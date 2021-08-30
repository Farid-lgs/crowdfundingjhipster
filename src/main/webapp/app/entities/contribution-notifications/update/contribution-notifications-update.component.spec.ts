jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContributionNotificationsService } from '../service/contribution-notifications.service';
import { IContributionNotifications, ContributionNotifications } from '../contribution-notifications.model';

import { ContributionNotificationsUpdateComponent } from './contribution-notifications-update.component';

describe('Component Tests', () => {
  describe('ContributionNotifications Management Update Component', () => {
    let comp: ContributionNotificationsUpdateComponent;
    let fixture: ComponentFixture<ContributionNotificationsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let contributionNotificationsService: ContributionNotificationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ContributionNotificationsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ContributionNotificationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContributionNotificationsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      contributionNotificationsService = TestBed.inject(ContributionNotificationsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const contributionNotifications: IContributionNotifications = { id: 456 };

        activatedRoute.data = of({ contributionNotifications });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(contributionNotifications));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ContributionNotifications>>();
        const contributionNotifications = { id: 123 };
        jest.spyOn(contributionNotificationsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contributionNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contributionNotifications }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(contributionNotificationsService.update).toHaveBeenCalledWith(contributionNotifications);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ContributionNotifications>>();
        const contributionNotifications = new ContributionNotifications();
        jest.spyOn(contributionNotificationsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contributionNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contributionNotifications }));
        saveSubject.complete();

        // THEN
        expect(contributionNotificationsService.create).toHaveBeenCalledWith(contributionNotifications);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ContributionNotifications>>();
        const contributionNotifications = { id: 123 };
        jest.spyOn(contributionNotificationsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contributionNotifications });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(contributionNotificationsService.update).toHaveBeenCalledWith(contributionNotifications);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
