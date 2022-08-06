import { IArrivage } from 'app/entities/arrivage/arrivage.model';

export interface IFacture {
  id?: number;
  idFacture?: number | null;
  montant?: number | null;
  arrivage?: IArrivage | null;
}

export class Facture implements IFacture {
  constructor(public id?: number, public idFacture?: number | null, public montant?: number | null, public arrivage?: IArrivage | null) {}
}

export function getFactureIdentifier(facture: IFacture): number | undefined {
  return facture.id;
}
