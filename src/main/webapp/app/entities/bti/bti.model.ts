import dayjs from 'dayjs/esm';
import { IProduit } from 'app/entities/produit/produit.model';
import { IMagazin } from 'app/entities/magazin/magazin.model';

export interface IBti {
  id?: number;
  numOrdre?: number | null;
  date?: dayjs.Dayjs | null;
  ref?: number | null;
  qte?: number | null;
  produit?: IProduit | null;
  magazin?: IMagazin | null;
}

export class Bti implements IBti {
  constructor(
    public id?: number,
    public numOrdre?: number | null,
    public date?: dayjs.Dayjs | null,
    public ref?: number | null,
    public qte?: number | null,
    public produit?: IProduit | null,
    public magazin?: IMagazin | null
  ) {}
}

export function getBtiIdentifier(bti: IBti): number | undefined {
  return bti.id;
}
