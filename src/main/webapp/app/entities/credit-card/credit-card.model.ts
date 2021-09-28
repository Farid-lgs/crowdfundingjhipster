import * as dayjs from 'dayjs';
import { IUserInfos } from 'app/entities/user-infos/user-infos.model';

export interface ICreditCard {
  id?: number | null;
  number?: number | null;
  ownerName?: string | null;
  key?: number | null;
  expirationDate?: dayjs.Dayjs | null;
  userInfos?: IUserInfos | null;
}

export class CreditCard implements ICreditCard {
  constructor(
    public id?: number | null,
    public number?: number | null,
    public ownerName?: string | null,
    public key?: number | null,
    public expirationDate?: dayjs.Dayjs | null,
    public userInfos?: IUserInfos | null
  ) {}
}

export function getCreditCardIdentifier(creditCard: ICreditCard): number | null | undefined {
  return creditCard.id;
}
