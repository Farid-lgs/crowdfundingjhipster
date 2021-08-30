import * as dayjs from 'dayjs';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { IProject } from 'app/entities/project/project.model';
import { ICommunityNotifications } from 'app/entities/community-notifications/community-notifications.model';

export interface ICommunity {
  id?: number;
  name?: string;
  nameFr?: string | null;
  createdAt?: dayjs.Dayjs | null;
  about?: string;
  coverImageContentType?: string | null;
  coverImage?: string | null;
  communityMembers?: ICommunityMembers | null;
  admin?: IUserInfos | null;
  projects?: IProject[] | null;
  communityNotifications?: ICommunityNotifications[] | null;
}

export class Community implements ICommunity {
  constructor(
    public id?: number,
    public name?: string,
    public nameFr?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public about?: string,
    public coverImageContentType?: string | null,
    public coverImage?: string | null,
    public communityMembers?: ICommunityMembers | null,
    public admin?: IUserInfos | null,
    public projects?: IProject[] | null,
    public communityNotifications?: ICommunityNotifications[] | null
  ) {}
}

export function getCommunityIdentifier(community: ICommunity): number | undefined {
  return community.id;
}
