import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectCommentDetailComponent } from './project-comment-detail.component';

describe('Component Tests', () => {
  describe('ProjectComment Management Detail Component', () => {
    let comp: ProjectCommentDetailComponent;
    let fixture: ComponentFixture<ProjectCommentDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ProjectCommentDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ projectComment: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ProjectCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectComment).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
