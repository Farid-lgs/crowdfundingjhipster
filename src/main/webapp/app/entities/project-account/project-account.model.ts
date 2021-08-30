import { IProject } from 'app/entities/project/project.model';

export interface IProjectAccount {
  id?: number;
  balance?: string | null;
  number?: number | null;
  bank?: string | null;
  project?: IProject | null;
}

export class ProjectAccount implements IProjectAccount {
  constructor(
    public id?: number,
    public balance?: string | null,
    public number?: number | null,
    public bank?: string | null,
    public project?: IProject | null
  ) {}
}

export function getProjectAccountIdentifier(projectAccount: IProjectAccount): number | undefined {
  return projectAccount.id;
}
