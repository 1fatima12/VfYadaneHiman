import { IProduit } from 'app/entities/produit/produit.model';

export interface IMarque {
  id?: number;
  nomMarque?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  produits?: IProduit[] | null;
}

export class Marque implements IMarque {
  constructor(
    public id?: number,
    public nomMarque?: string | null,
    public logoContentType?: string | null,
    public logo?: string | null,
    public produits?: IProduit[] | null
  ) {}
}

export function getMarqueIdentifier(marque: IMarque): number | undefined {
  return marque.id;
}
