import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommunityNotificationsDetailComponent } from './community-notifications-detail.component';

describe('Component Tests', () => {
  describe('CommunityNotifications Management Detail Component', () => {
    let comp: CommunityNotificationsDetailComponent;
    let fixture: ComponentFixture<CommunityNotificationsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CommunityNotificationsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ communityNotifications: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CommunityNotificationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommunityNotificationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load communityNotifications on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.communityNotifications).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
