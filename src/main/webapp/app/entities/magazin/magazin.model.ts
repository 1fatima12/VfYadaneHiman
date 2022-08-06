import { ILocation } from 'app/entities/location/location.model';

export interface IMagazin {
  id?: number;
  nomMagazin?: string | null;
  adresseMagazin?: string | null;
  location?: ILocation | null;
}

export class Magazin implements IMagazin {
  constructor(
    public id?: number,
    public nomMagazin?: string | null,
    public adresseMagazin?: string | null,
    public location?: ILocation | null
  ) {}
}

export function getMagazinIdentifier(magazin: IMagazin): number | undefined {
  return magazin.id;
}
