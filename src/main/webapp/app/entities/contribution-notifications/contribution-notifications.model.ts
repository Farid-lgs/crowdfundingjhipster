import * as dayjs from 'dayjs';
import { IContribution } from 'app/entities/contribution/contribution.model';

export interface IContributionNotifications {
  id?: number;
  content?: string | null;
  sentAt?: dayjs.Dayjs | null;
  contribution?: IContribution | null;
}

export class ContributionNotifications implements IContributionNotifications {
  constructor(
    public id?: number,
    public content?: string | null,
    public sentAt?: dayjs.Dayjs | null,
    public contribution?: IContribution | null
  ) {}
}

export function getContributionNotificationsIdentifier(contributionNotifications: IContributionNotifications): number | undefined {
  return contributionNotifications.id;
}
