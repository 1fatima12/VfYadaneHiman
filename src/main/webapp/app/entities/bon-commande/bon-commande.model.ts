import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { IProduit } from 'app/entities/produit/produit.model';

export interface IBonCommande {
  id?: number;
  qteCommandee?: number | null;
  commandeF?: ICommandeFournisseur | null;
  commandeC?: ICommandeClient | null;
  produit?: IProduit | null;
}

export class BonCommande implements IBonCommande {
  constructor(
    public id?: number,
    public qteCommandee?: number | null,
    public commandeF?: ICommandeFournisseur | null,
    public commandeC?: ICommandeClient | null,
    public produit?: IProduit | null
  ) {}
}

export function getBonCommandeIdentifier(bonCommande: IBonCommande): number | undefined {
  return bonCommande.id;
}
