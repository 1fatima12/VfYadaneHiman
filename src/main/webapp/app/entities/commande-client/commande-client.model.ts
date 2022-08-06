import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';

export interface ICommandeClient {
  id?: number;
  dateCom?: dayjs.Dayjs | null;
  designation?: string | null;
  client?: IClient | null;
}

export class CommandeClient implements ICommandeClient {
  constructor(
    public id?: number,
    public dateCom?: dayjs.Dayjs | null,
    public designation?: string | null,
    public client?: IClient | null
  ) {}
}

export function getCommandeClientIdentifier(commandeClient: ICommandeClient): number | undefined {
  return commandeClient.id;
}
