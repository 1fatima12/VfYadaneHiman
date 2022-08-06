import { IEmploye } from 'app/entities/employe/employe.model';
import { IClient } from 'app/entities/client/client.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IMagazin } from 'app/entities/magazin/magazin.model';

export interface ILocation {
  id?: number;
  rueAddress?: string | null;
  codePostal?: string | null;
  ville?: string | null;
  stateProvince?: string | null;
  employe?: IEmploye | null;
  client?: IClient | null;
  fournisseur?: IFournisseur | null;
  magazin?: IMagazin | null;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public rueAddress?: string | null,
    public codePostal?: string | null,
    public ville?: string | null,
    public stateProvince?: string | null,
    public employe?: IEmploye | null,
    public client?: IClient | null,
    public fournisseur?: IFournisseur | null,
    public magazin?: IMagazin | null
  ) {}
}

export function getLocationIdentifier(location: ILocation): number | undefined {
  return location.id;
}
