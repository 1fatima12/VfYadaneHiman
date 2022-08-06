import dayjs from 'dayjs/esm';
import { ILocation } from 'app/entities/location/location.model';
import { IMagazin } from 'app/entities/magazin/magazin.model';

export interface IEmploye {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  email?: string | null;
  adresse?: string | null;
  age?: number | null;
  photoContentType?: string | null;
  photo?: string | null;
  poste?: string | null;
  salaire?: number | null;
  dateEmbauche?: dayjs.Dayjs | null;
  location?: ILocation | null;
  magzin?: IMagazin | null;
}

export class Employe implements IEmploye {
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
    public poste?: string | null,
    public salaire?: number | null,
    public dateEmbauche?: dayjs.Dayjs | null,
    public location?: ILocation | null,
    public magzin?: IMagazin | null
  ) {}
}

export function getEmployeIdentifier(employe: IEmploye): number | undefined {
  return employe.id;
}
