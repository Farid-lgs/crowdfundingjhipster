import * as dayjs from 'dayjs';
import { IProjectImages } from 'app/entities/project-images/project-images.model';
import { IContribution } from 'app/entities/contribution/contribution.model';
import { IProjectAccount } from 'app/entities/project-account/project-account.model';
import { IProjectComment } from 'app/entities/project-comment/project-comment.model';
import { IReward } from 'app/entities/reward/reward.model';
import { ICommunity } from 'app/entities/community/community.model';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { ICategory } from 'app/entities/category/category.model';
import { ProjectStatuts } from 'app/entities/enumerations/project-statuts.model';

export interface IProject {
  id?: number;
  title?: string;
  goal?: number;
  headline?: string;
  videoUrl?: string | null;
  location?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  description?: string | null;
  moreLinks?: string | null;
  budgetDescription?: string | null;
  duration?: number;
  adminNotes?: string | null;
  coverImageContentType?: string | null;
  coverImage?: string | null;
  status?: ProjectStatuts | null;
  commonId?: string | null;
  projectImages?: IProjectImages[] | null;
  contributions?: IContribution[] | null;
  projectAccounts?: IProjectAccount[] | null;
  projectPosts?: IProjectComment[] | null;
  rewards?: IReward[] | null;
  participants?: number | null;
  amount?: number | null;
  community?: ICommunity | null;
  userInfos?: IUserInfos | null;
  category?: ICategory | null;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public title?: string,
    public goal?: number,
    public headline?: string,
    public videoUrl?: string | null,
    public location?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedAt?: dayjs.Dayjs | null,
    public description?: string | null,
    public moreLinks?: string | null,
    public budgetDescription?: string | null,
    public duration?: number,
    public adminNotes?: string | null,
    public coverImageContentType?: string | null,
    public coverImage?: string | null,
    public status?: ProjectStatuts | null,
    public commonId?: string | null,
    public projectImages?: IProjectImages[] | null,
    public contributions?: IContribution[] | null,
    public projectAccounts?: IProjectAccount[] | null,
    public projectPosts?: IProjectComment[] | null,
    public rewards?: IReward[] | null,
    public participants?: number | null,
    public amount?: number | null,
    public community?: ICommunity | null,
    public userInfos?: IUserInfos | null,
    public category?: ICategory | null
  ) { }
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
