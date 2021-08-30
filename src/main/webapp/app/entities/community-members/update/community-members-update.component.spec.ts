jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CommunityMembersService } from '../service/community-members.service';
import { ICommunityMembers, CommunityMembers } from '../community-members.model';

import { CommunityMembersUpdateComponent } from './community-members-update.component';

describe('Component Tests', () => {
  describe('CommunityMembers Management Update Component', () => {
    let comp: CommunityMembersUpdateComponent;
    let fixture: ComponentFixture<CommunityMembersUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let communityMembersService: CommunityMembersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CommunityMembersUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CommunityMembersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunityMembersUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      communityMembersService = TestBed.inject(CommunityMembersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const communityMembers: ICommunityMembers = { id: 456 };

        activatedRoute.data = of({ communityMembers });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(communityMembers));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityMembers>>();
        const communityMembers = { id: 123 };
        jest.spyOn(communityMembersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityMembers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: communityMembers }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(communityMembersService.update).toHaveBeenCalledWith(communityMembers);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityMembers>>();
        const communityMembers = new CommunityMembers();
        jest.spyOn(communityMembersService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityMembers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: communityMembers }));
        saveSubject.complete();

        // THEN
        expect(communityMembersService.create).toHaveBeenCalledWith(communityMembers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CommunityMembers>>();
        const communityMembers = { id: 123 };
        jest.spyOn(communityMembersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ communityMembers });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(communityMembersService.update).toHaveBeenCalledWith(communityMembers);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
