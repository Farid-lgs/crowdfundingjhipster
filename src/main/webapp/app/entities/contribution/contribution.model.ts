import * as dayjs from 'dayjs';
import { IContributionNotifications } from 'app/entities/contribution-notifications/contribution-notifications.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { IProject } from 'app/entities/project/project.model';

export interface IContribution {
  id?: number;
  amount?: number;
  payerName?: string;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  anonymous?: boolean;
  rewarded?: boolean;
  contributionNotifications?: IContributionNotifications | null;
  userInfos?: IUserInfos | null;
  project?: IProject | null;
}

export class Contribution implements IContribution {
  constructor(
    public id?: number,
    public amount?: number,
    public payerName?: string,
    public createdAt?: dayjs.Dayjs | null,
    public updatedAt?: dayjs.Dayjs | null,
    public anonymous?: boolean,
    public rewarded?: boolean,
    public contributionNotifications?: IContributionNotifications | null,
    public userInfos?: IUserInfos | null,
    public project?: IProject | null
  ) {
    this.anonymous = this.anonymous ?? false;
    this.rewarded = this.rewarded ?? false;
  }
}

export function getContributionIdentifier(contribution: IContribution): number | undefined {
  return contribution.id;
}
