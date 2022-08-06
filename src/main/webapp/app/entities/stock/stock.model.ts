import { IProduit } from 'app/entities/produit/produit.model';
import { IMagazin } from 'app/entities/magazin/magazin.model';

export interface IStock {
  id?: number;
  qte?: number | null;
  produit?: IProduit | null;
  magazin?: IMagazin | null;
}

export class Stock implements IStock {
  constructor(public id?: number, public qte?: number | null, public produit?: IProduit | null, public magazin?: IMagazin | null) {}
}

export function getStockIdentifier(stock: IStock): number | undefined {
  return stock.id;
}
