import { IAddress } from 'app/entities/address/address.model';

export interface ICountry {
  id?: number;
  name?: string;
  nameFr?: string | null;
  addresses?: IAddress[] | null;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string, public nameFr?: string | null, public addresses?: IAddress[] | null) {}
}

export function getCountryIdentifier(country: ICountry): number | undefined {
  return country.id;
}
