import { ILocation } from 'app/entities/location/location.model';

export interface IClient {
  id?: number;
  numProd?: number | null;
  nomProd?: string | null;
  status?: boolean | null;
  prixVente?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  ice?: string | null;
  location?: ILocation | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public numProd?: number | null,
    public nomProd?: string | null,
    public status?: boolean | null,
    public prixVente?: number | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public ice?: string | null,
    public location?: ILocation | null
  ) {
    this.status = this.status ?? false;
  }
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
