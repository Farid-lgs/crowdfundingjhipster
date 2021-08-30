import * as dayjs from 'dayjs';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';

export interface ICreditCard {
  id?: number;
  number?: number;
  ownerName?: string | null;
  key?: number;
  expirationDate?: dayjs.Dayjs | null;
  userInfos?: IUserInfos | null;
}

export class CreditCard implements ICreditCard {
  constructor(
    public id?: number,
    public number?: number,
    public ownerName?: string | null,
    public key?: number,
    public expirationDate?: dayjs.Dayjs | null,
    public userInfos?: IUserInfos | null
  ) {}
}

export function getCreditCardIdentifier(creditCard: ICreditCard): number | undefined {
  return creditCard.id;
}
