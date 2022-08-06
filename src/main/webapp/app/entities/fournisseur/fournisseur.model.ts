import { ILocation } from 'app/entities/location/location.model';

export interface IFournisseur {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  email?: string | null;
  adresse?: string | null;
  age?: number | null;
  photoContentType?: string | null;
  photo?: string | null;
  entreprise?: string | null;
  fax?: string | null;
  ice?: string | null;
  location?: ILocation | null;
}

export class Fournisseur implements IFournisseur {
  constructor(
    public id?: number,
    public nom?: string | null,
    public prenom?: string | null,
    public telephone?: string | null,
    public email?: string | null,
    public adresse?: string | null,
    public age?: number | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public entreprise?: string | null,
    public fax?: string | null,
    public ice?: string | null,
    public location?: ILocation | null
  ) {}
}

export function getFournisseurIdentifier(fournisseur: IFournisseur): number | undefined {
  return fournisseur.id;
}
