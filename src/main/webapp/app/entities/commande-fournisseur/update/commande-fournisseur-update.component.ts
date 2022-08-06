import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { ICommandeFournisseur, CommandeFournisseur } from '../commande-fournisseur.model';
import { CommandeFournisseurService } from '../service/commande-fournisseur.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { BonCommandeService } from 'app/entities/bon-commande/service/bon-commande.service';
import { IBonCommande } from 'app/entities/bon-commande/bon-commande.model';

@Component({
  selector: 'jhi-commande-fournisseur-update',
  templateUrl: './commande-fournisseur-update.component.html',
})
export class CommandeFournisseurUpdateComponent implements OnInit {
  isSaving = false;
  fournisseursSharedCollection: IFournisseur[] = [];
  produitsList:IProduit[]|any;
  results : IProduit[]|any;
  results1 : number[]|any
  achat: IBonCommande ={};
    resultat: IBonCommande ={};

  qte:number[] =[];
 produits:IProduit[]=[];
 produit:IProduit |any;
 currentCommandeF:ICommandeFournisseur={} ;
  editForm = this.fb.group({
    id: [],
    dateCom: [],
    designation: [],
    fournisseur: [],
  });

  constructor(
    protected commandeFournisseurService: CommandeFournisseurService,
    protected fournisseurService: FournisseurService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected productService : ProduitService,
    protected bonCommande : BonCommandeService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeFournisseur }) => {

      this.updateForm(commandeFournisseur);
      this.loadRelationshipsOptions();

    });
        this.getProducts();
   
  }
  getProducts():void {
  this.productService.getProduits().subscribe(
	(response : IProduit[])=>{
		this.produitsList=response;
	}  
	
);

}
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeFournisseur = this.createFromForm();
    if (commandeFournisseur.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeFournisseurService.update(commandeFournisseur));

    } else {
      this.subscribeToSaveResponse(this.commandeFournisseurService.create(commandeFournisseur));
    }
  }

  trackFournisseurById(_index: number, item: IFournisseur): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeFournisseur>>): void {
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

  protected updateForm(commandeFournisseur: ICommandeFournisseur): void {
    this.editForm.patchValue({
      id: commandeFournisseur.id,
      dateCom: commandeFournisseur.dateCom,
      designation: commandeFournisseur.designation,
      fournisseur: commandeFournisseur.fournisseur,
      
    });

    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing(
      this.fournisseursSharedCollection,
      commandeFournisseur.fournisseur
    );

  }

  protected loadRelationshipsOptions(): void {
    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing(fournisseurs, this.editForm.get('fournisseur')!.value)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));
  }

  protected createFromForm(): ICommandeFournisseur {
    return {
      ...new CommandeFournisseur(),
      
      id: this.editForm.get(['id'])!.value,
      dateCom: this.editForm.get(['dateCom'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      fournisseur: this.editForm.get(['fournisseur'])!.value,
    };
  }
 // eslint-disable-next-line @typescript-eslint/member-ordering

// eslint-disable-next-line @typescript-eslint/member-ordering
public searchProduit(key: string): void {
  const results: IProduit[] = [];

  for (const prod of this.produitsList) {
    if (prod.nomProd&&prod.nomProd.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
	
      results.push(prod);
    }
  }
  this.produitsList = results;
  if (results.length === 0 || !key) {
    this.getProducts();
  }
}
// eslint-disable-next-line @typescript-eslint/member-ordering
public AjoutAuPanier():void{
//	
	
} 
	 

	 // eslint-disable-next-line @typescript-eslint/member-ordering
	 commandeFournisseur = this.createFromForm();

// eslint-disable-next-line @typescript-eslint/member-ordering
public onOpenModal(produito?: IProduit  ):void{
   const result: IProduit[] =[];
 const container=document.getElementById('main-container');
  const button=document.createElement("button");
  button.type='button';
  button.style.display='none';
  button.setAttribute('data-toggle','modal');
  this.produit=produito;
  result.push(this.produit);
  this.achat.produit=produito;
  
  this.achat.commandeF=this.commandeFournisseur;
  this.achat.qteCommandee=parseInt((document.getElementById('form1') as HTMLInputElement).value, 10);
this.bonCommande.addBonCommande(this.achat).subscribe(
    (response: IBonCommande)=>{
	  this.resultat= response;
    },
    (error: HttpErrorResponse)=>{
      alert(error.message);
    }
  );;
  
  // eslint-disable-next-line prefer-const
  for(const prod of result){
	if(this.produits.includes(prod)){
		alert("ce produit deja existe")
	}else{	this.produits.push(prod);
}
}
  button.setAttribute('data-target','#ajouterPanier');
  container?.appendChild(button);
  button.click();

  }

}
