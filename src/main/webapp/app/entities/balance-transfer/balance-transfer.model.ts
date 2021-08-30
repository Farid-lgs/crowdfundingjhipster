import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { IProject } from 'app/entities/project/project.model';

export interface IBalanceTransfer {
  id?: number;
  amount?: number;
  userInfos?: IUserInfos | null;
  project?: IProject | null;
}

export class BalanceTransfer implements IBalanceTransfer {
  constructor(public id?: number, public amount?: number, public userInfos?: IUserInfos | null, public project?: IProject | null) {}
}

export function getBalanceTransferIdentifier(balanceTransfer: IBalanceTransfer): number | undefined {
  return balanceTransfer.id;
}
