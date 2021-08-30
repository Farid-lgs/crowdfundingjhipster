import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommunityMembersDetailComponent } from './community-members-detail.component';

describe('Component Tests', () => {
  describe('CommunityMembers Management Detail Component', () => {
    let comp: CommunityMembersDetailComponent;
    let fixture: ComponentFixture<CommunityMembersDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CommunityMembersDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ communityMembers: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CommunityMembersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommunityMembersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load communityMembers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.communityMembers).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
