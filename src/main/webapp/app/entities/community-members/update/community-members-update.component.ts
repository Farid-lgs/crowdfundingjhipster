import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICommunityMembers, CommunityMembers } from '../community-members.model';
import { CommunityMembersService } from '../service/community-members.service';

@Component({
  selector: 'jhi-community-members-update',
  templateUrl: './community-members-update.component.html',
})
export class CommunityMembersUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(
    protected communityMembersService: CommunityMembersService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communityMembers }) => {
      this.updateForm(communityMembers);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const communityMembers = this.createFromForm();
    if (communityMembers.id !== undefined) {
      this.subscribeToSaveResponse(this.communityMembersService.update(communityMembers));
    } else {
      this.subscribeToSaveResponse(this.communityMembersService.create(communityMembers));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunityMembers>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(communityMembers: ICommunityMembers): void {
    this.editForm.patchValue({
      id: communityMembers.id,
    });
  }

  protected createFromForm(): ICommunityMembers {
    return {
      ...new CommunityMembers(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
