import dayjs from 'dayjs/esm';
import { IProduit } from 'app/entities/produit/produit.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IFacture } from 'app/entities/facture/facture.model';

export interface IArrivage {
  id?: number;
  dateArrivage?: dayjs.Dayjs | null;
  prixAchat?: number | null;
  produit?: IProduit | null;
  fournisseur?: IFournisseur | null;
  facture?: IFacture | null;
}

export class Arrivage implements IArrivage {
  constructor(
    public id?: number,
    public dateArrivage?: dayjs.Dayjs | null,
    public prixAchat?: number | null,
    public produit?: IProduit | null,
    public fournisseur?: IFournisseur | null,
    public facture?: IFacture | null
  ) {}
}

export function getArrivageIdentifier(arrivage: IArrivage): number | undefined {
  return arrivage.id;
}
