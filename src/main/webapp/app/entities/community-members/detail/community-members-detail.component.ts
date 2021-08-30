import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommunityMembers } from '../community-members.model';

@Component({
  selector: 'jhi-community-members-detail',
  templateUrl: './community-members-detail.component.html',
})
export class CommunityMembersDetailComponent implements OnInit {
  communityMembers: ICommunityMembers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communityMembers }) => {
      this.communityMembers = communityMembers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
