import { IProject } from 'app/entities/project/project.model';

export interface IProjectImages {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
  project?: IProject | null;
}

export class ProjectImages implements IProjectImages {
  constructor(
    public id?: number,
    public imageContentType?: string | null,
    public image?: string | null,
    public project?: IProject | null
  ) {}
}

export function getProjectImagesIdentifier(projectImages: IProjectImages): number | undefined {
  return projectImages.id;
}
