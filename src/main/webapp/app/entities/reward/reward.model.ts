import * as dayjs from 'dayjs';
import { IProject } from 'app/entities/project/project.model';

export interface IReward {
  id?: number;
  title?: string;
  description?: string;
  minimumValue?: number;
  deliverAt?: dayjs.Dayjs | null;
  expiresAt?: dayjs.Dayjs | null;
  maximumContributions?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  project?: IProject | null;
}

export class Reward implements IReward {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public minimumValue?: number,
    public deliverAt?: dayjs.Dayjs | null,
    public expiresAt?: dayjs.Dayjs | null,
    public maximumContributions?: number | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public project?: IProject | null
  ) {}
}

export function getRewardIdentifier(reward: IReward): number | undefined {
  return reward.id;
}
