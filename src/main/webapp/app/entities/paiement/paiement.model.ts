import { ModePaiement } from 'app/entities/enumerations/mode-paiement.model';

export interface IPaiement {
  id?: number;
  type?: ModePaiement | null;
  avance?: number | null;
  etat?: boolean | null;
}

export class Paiement implements IPaiement {
  constructor(public id?: number, public type?: ModePaiement | null, public avance?: number | null, public etat?: boolean | null) {
    this.etat = this.etat ?? false;
  }
}

export function getPaiementIdentifier(paiement: IPaiement): number | undefined {
  return paiement.id;
}
