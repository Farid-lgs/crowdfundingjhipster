import * as dayjs from 'dayjs';
import { IUser } from 'app/entities/user/user.model';
import { IContribution } from 'app/entities/contribution/contribution.model';
import { IProject } from 'app/entities/project/project.model';
import { IProjectComment } from 'app/entities/project-comment/project-comment.model';
import { ICommunity } from 'app/entities/community/community.model';
import { IAddress } from 'app/entities/address/address.model';
import { IBalanceTransfer } from 'app/entities/balance-transfer/balance-transfer.model';
import { ICreditCard } from 'app/entities/credit-card/credit-card.model';
import { ICommunityMembers } from 'app/entities/community-members/community-members.model';

export interface IUserInfos {
  id?: number;
  publicName?: string;
  birthDate?: dayjs.Dayjs | null;
  twitter?: string | null;
  facebook?: string | null;
  linkedIn?: string | null;
  description?: string | null;
  coverImageContentType?: string | null;
  coverImage?: string | null;
  commonId?: string | null;
  user?: IUser | null;
  contributions?: IContribution[] | null;
  projects?: IProject[] | null;
  projectPosts?: IProjectComment[] | null;
  community?: ICommunity | null;
  address?: IAddress | null;
  balanceTransfer?: IBalanceTransfer | null;
  creditCard?: ICreditCard | null;
  communityMembers?: ICommunityMembers | null;
}

export class UserInfos implements IUserInfos {
  constructor(
    public id?: number,
    public publicName?: string,
    public birthDate?: dayjs.Dayjs | null,
    public twitter?: string | null,
    public facebook?: string | null,
    public linkedIn?: string | null,
    public description?: string | null,
    public coverImageContentType?: string | null,
    public coverImage?: string | null,
    public commonId?: string | null,
    public user?: IUser | null,
    public contributions?: IContribution[] | null,
    public projects?: IProject[] | null,
    public projectPosts?: IProjectComment[] | null,
    public community?: ICommunity | null,
    public address?: IAddress | null,
    public balanceTransfer?: IBalanceTransfer | null,
    public creditCard?: ICreditCard | null,
    public communityMembers?: ICommunityMembers | null
  ) {}
}

export function getUserInfosIdentifier(userInfos: IUserInfos): number | undefined {
  return userInfos.id;
}
