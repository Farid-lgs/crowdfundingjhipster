import { IUserInfos } from 'app/entities/user-infos/user-infos.model';
import { ICountry } from 'app/entities/country/country.model';

export interface IAddress {
  id?: number;
  address?: string | null;
  city?: string | null;
  state?: string | null;
  zipCode?: string | null;
  phoneNumber?: string | null;
  userInfos?: IUserInfos | null;
  country?: ICountry | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public address?: string | null,
    public city?: string | null,
    public state?: string | null,
    public zipCode?: string | null,
    public phoneNumber?: string | null,
    public userInfos?: IUserInfos | null,
    public country?: ICountry | null
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
