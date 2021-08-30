import { IUserInfos } from 'app/entities/user-infos/user-infos.model';

export interface ICommunityMembers {
  id?: number;
  userInfos?: IUserInfos[] | null;
}

export class CommunityMembers implements ICommunityMembers {
  constructor(public id?: number, public userInfos?: IUserInfos[] | null) {}
}

export function getCommunityMembersIdentifier(communityMembers: ICommunityMembers): number | undefined {
  return communityMembers.id;
}
