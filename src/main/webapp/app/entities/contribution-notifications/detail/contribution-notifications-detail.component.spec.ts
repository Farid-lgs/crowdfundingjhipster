import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContributionNotificationsDetailComponent } from './contribution-notifications-detail.component';

describe('Component Tests', () => {
  describe('ContributionNotifications Management Detail Component', () => {
    let comp: ContributionNotificationsDetailComponent;
    let fixture: ComponentFixture<ContributionNotificationsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ContributionNotificationsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ contributionNotifications: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ContributionNotificationsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContributionNotificationsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contributionNotifications on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contributionNotifications).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
