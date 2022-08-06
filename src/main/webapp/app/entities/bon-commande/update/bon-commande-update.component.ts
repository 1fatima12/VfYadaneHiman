import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map ,startWith} from 'rxjs/operators';

import { IBonCommande, BonCommande } from '../bon-commande.model';
import { BonCommandeService } from '../service/bon-commande.service';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { CommandeFournisseurService } from 'app/entities/commande-fournisseur/service/commande-fournisseur.service';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { CommandeClientService } from 'app/entities/commande-client/service/commande-client.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
@Component({
  selector: 'jhi-bon-commande-update',
  templateUrl: './bon-commande-update.component.html',
})
export class BonCommandeUpdateComponent implements OnInit {
	 public countryList= [
 {name:"India",code:"IN"},
 {name:"United Nation America",code:"US"},
 {name:"Canada",code:"CN"}
]


  isSaving = false;
 myControl = new FormControl('');
 
  filteredOptions: Observable<string[]> | undefined;
  commandeFournisseursSharedCollection: ICommandeFournisseur[] = [];
  commandeClientsSharedCollection: ICommandeClient[] = [];
  produitsSharedCollection: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    qteCommandee: [],
    commandeF: [],
    commandeC: [],
    produit: [],
  });

  constructor(
    protected bonCommandeService: BonCommandeService,
    protected commandeFournisseurService: CommandeFournisseurService,
    protected commandeClientService: CommandeClientService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}
  
  ngOnInit(): void {
	this.filteredOptions = this.myControl.valueChanges
      .pipe(
      startWith(''),
      map(val => this.filter(val))
      );
    this.activatedRoute.data.subscribe(({ bonCommande }) => {
      this.updateForm(bonCommande);

      this.loadRelationshipsOptions();
    });
    
  }
  
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonCommande = this.createFromForm();
    if (bonCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.bonCommandeService.update(bonCommande));
    } else {
      this.subscribeToSaveResponse(this.bonCommandeService.create(bonCommande));
    }
  }

  trackCommandeFournisseurById(_index: number, item: ICommandeFournisseur): number {
    return item.id!;
  }

  trackCommandeClientById(_index: number, item: ICommandeClient): number {
    return item.id!;
  }

  trackProduitById(_index: number, item: IProduit): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonCommande>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bonCommande: IBonCommande): void {
    this.editForm.patchValue({
      id: bonCommande.id,
      qteCommandee: bonCommande.qteCommandee,
      commandeF: bonCommande.commandeF,
      commandeC: bonCommande.commandeC,
      produit: bonCommande.produit,
    });

    this.commandeFournisseursSharedCollection = this.commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing(
      this.commandeFournisseursSharedCollection,
      bonCommande.commandeF
    );
    this.commandeClientsSharedCollection = this.commandeClientService.addCommandeClientToCollectionIfMissing(
      this.commandeClientsSharedCollection,
      bonCommande.commandeC
    );
    this.produitsSharedCollection = this.produitService.addProduitToCollectionIfMissing(this.produitsSharedCollection, bonCommande.produit);
  }

  protected loadRelationshipsOptions(): void {
    this.commandeFournisseurService
      .query()
      .pipe(map((res: HttpResponse<ICommandeFournisseur[]>) => res.body ?? []))
      .pipe(
        map((commandeFournisseurs: ICommandeFournisseur[]) =>
          this.commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing(
            commandeFournisseurs,
            this.editForm.get('commandeF')!.value
          )
        )
      )
      .subscribe((commandeFournisseurs: ICommandeFournisseur[]) => (this.commandeFournisseursSharedCollection = commandeFournisseurs));

    this.commandeClientService
      .query()
      .pipe(map((res: HttpResponse<ICommandeClient[]>) => res.body ?? []))
      .pipe(
        map((commandeClients: ICommandeClient[]) =>
          this.commandeClientService.addCommandeClientToCollectionIfMissing(commandeClients, this.editForm.get('commandeC')!.value)
        )
      )
      .subscribe((commandeClients: ICommandeClient[]) => (this.commandeClientsSharedCollection = commandeClients));

    this.produitService
      .query()
      .pipe(map((res: HttpResponse<IProduit[]>) => res.body ?? []))
      .pipe(
        map((produits: IProduit[]) => this.produitService.addProduitToCollectionIfMissing(produits, this.editForm.get('produit')!.value))
      )
      .subscribe((produits: IProduit[]) => (this.produitsSharedCollection = produits));
  }

  protected createFromForm(): IBonCommande {
    return {
      ...new BonCommande(),
      id: this.editForm.get(['id'])!.value,
      qteCommandee: this.editForm.get(['qteCommandee'])!.value,
      commandeF: this.editForm.get(['commandeF'])!.value,
      commandeC: this.editForm.get(['commandeC'])!.value,
      produit: this.editForm.get(['produit'])!.value,
    };
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  filter(val: string): string[] {
    return this.newMethod(val);
  }



    private newMethod(val: string): string[] {
        return newFunction();

        function newFunction(this: any): string[] {
            // eslint-disable-next-line @typescript-eslint/no-unsafe-return
            return this.produitsSharedCollection.map((x: IProduit) => x.nomProd).filter((option: string) => option.toLowerCase().includes(val.toLowerCase()));
        }
    }
     // eslint-disable-next-line @typescript-eslint/member-ordering
     displayFn(prod: IProduit): string{
    // eslint-disable-next-line @typescript-eslint/prefer-optional-chain
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    // eslint-disable-next-line @typescript-eslint/prefer-optional-chain
    return prod .nomProd ? prod .nomProd : '';
  }
}
