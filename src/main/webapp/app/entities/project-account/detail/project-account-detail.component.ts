import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectAccount } from '../project-account.model';

@Component({
  selector: 'jhi-project-account-detail',
  templateUrl: './project-account-detail.component.html',
})
export class ProjectAccountDetailComponent implements OnInit {
  projectAccount: IProjectAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectAccount }) => {
      this.projectAccount = projectAccount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
