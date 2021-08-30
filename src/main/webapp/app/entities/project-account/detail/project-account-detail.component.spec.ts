import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectAccountDetailComponent } from './project-account-detail.component';

describe('Component Tests', () => {
  describe('ProjectAccount Management Detail Component', () => {
    let comp: ProjectAccountDetailComponent;
    let fixture: ComponentFixture<ProjectAccountDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ProjectAccountDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ projectAccount: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ProjectAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectAccount).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
