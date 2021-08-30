import { IProject } from 'app/entities/project/project.model';

export interface ICategory {
  id?: number;
  name?: string;
  nameFr?: string | null;
  projects?: IProject[] | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public nameFr?: string | null, public projects?: IProject[] | null) {}
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
