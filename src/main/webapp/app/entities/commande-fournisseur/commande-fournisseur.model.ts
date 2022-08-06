import dayjs from 'dayjs/esm';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';

export interface ICommandeFournisseur {
  id?: number;
  dateCom?: dayjs.Dayjs | null;
  designation?: string | null;
  fournisseur?: IFournisseur | null;
}

export class CommandeFournisseur implements ICommandeFournisseur {
  constructor(
    public id?: number,
    public dateCom?: dayjs.Dayjs | null,
    public designation?: string | null,
    public fournisseur?: IFournisseur | null
  ) {}
}

export function getCommandeFournisseurIdentifier(commandeFournisseur: ICommandeFournisseur): number | undefined {
  return commandeFournisseur.id;
}
