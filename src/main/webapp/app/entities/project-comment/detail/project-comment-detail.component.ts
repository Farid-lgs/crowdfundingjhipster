import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectComment } from '../project-comment.model';

@Component({
  selector: 'jhi-project-comment-detail',
  templateUrl: './project-comment-detail.component.html',
})
export class ProjectCommentDetailComponent implements OnInit {
  projectComment: IProjectComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectComment }) => {
      this.projectComment = projectComment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
