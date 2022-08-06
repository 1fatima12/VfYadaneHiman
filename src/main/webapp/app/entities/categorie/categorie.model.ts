import { IProduit } from 'app/entities/produit/produit.model';

export interface ICategorie {
  id?: number;
  nomCategorie?: string | null;
  description?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  produits?: IProduit[] | null;
}

export class Categorie implements ICategorie {
  constructor(
    public id?: number,
    public nomCategorie?: string | null,
    public description?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public produits?: IProduit[] | null
  ) {}
}

export function getCategorieIdentifier(categorie: ICategorie): number | undefined {
  return categorie.id;
}
