import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { IProject } from 'app/entities/project/project.model';

export interface IProjectComment {
  id?: number;
  comment?: string;
  userInfos?: IUserInfos | null;
  project?: IProject | null;
}

export class ProjectComment implements IProjectComment {
  constructor(public id?: number, public comment?: string, public userInfos?: IUserInfos | null, public project?: IProject | null) {}
}

export function getProjectCommentIdentifier(projectComment: IProjectComment): number | undefined {
  return projectComment.id;
}
