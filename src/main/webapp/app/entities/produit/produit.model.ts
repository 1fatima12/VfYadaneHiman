import { ICategorie } from 'app/entities/categorie/categorie.model';
import { IMarque } from 'app/entities/marque/marque.model';

export interface IProduit {
  [x: string]: any;
  id?: number;
  numProd?: number | null;
  nomProd?: string | null;
  status?: boolean | null;
  prixVente?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  categorie?: ICategorie | null;
  marque?: IMarque | null;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public numProd?: number | null,
    public nomProd?: string | null,
    public status?: boolean | null,
    public prixVente?: number | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public categorie?: ICategorie | null,
    public marque?: IMarque | null
  ) {
    this.status = this.status ?? false;
  }
}

export function getProduitIdentifier(produit: IProduit): number | undefined {
  return produit.id;
}
