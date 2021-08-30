import * as dayjs from 'dayjs';
import { ICommunity } from 'app/entities/community/community.model';

export interface ICommunityNotifications {
  id?: number;
  content?: string | null;
  sentAt?: dayjs.Dayjs | null;
  community?: ICommunity | null;
}

export class CommunityNotifications implements ICommunityNotifications {
  constructor(
    public id?: number,
    public content?: string | null,
    public sentAt?: dayjs.Dayjs | null,
    public community?: ICommunity | null
  ) {}
}

export function getCommunityNotificationsIdentifier(communityNotifications: ICommunityNotifications): number | undefined {
  return communityNotifications.id;
}
