import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReward } from '../reward.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-reward-detail',
  templateUrl: './reward-detail.component.html',
})
export class RewardDetailComponent implements OnInit {
  reward: IReward | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reward }) => {
      this.reward = reward;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
