import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { MarqueService } from 'app/entities/marque/service/marque.service';
import { IMarque } from 'app/entities/marque/marque.model';
import { IMagazin } from 'app/entities/magazin/magazin.model';
import { MagazinService } from 'app/entities/magazin/service/magazin.service';
import { StockService } from 'app/entities/stock/service/stock.service';
import { IStock } from 'app/entities/stock/stock.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
	 
	 
  account: Account | null = null;
  produitsList:IProduit[]|any;
  categorieList:ICategorie[] |any;
  marqueList:IMarque [] |any;
  categorie : ICategorie |any;
  magazinList : IMagazin[] |any;
  stockList : IStock[] |any;
    resultStock : IStock[] |any;
    url:any;
  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router,  
    protected productService : ProduitService,
        protected dataUtils: DataUtils,
        protected categorieService : CategorieService,
               protected marqueService : MarqueService,
               protected magazinService : MagazinService,
               protected stockService : StockService,

) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
              this.getProducts();
              this.getCategories();
              this.getMarques();
              this.getMagazins();
              

  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }
  getProducts():void{

	this.productService.getProduits().subscribe(
		(response:IProduit[])=>{
			this.produitsList=response;
		}
	)
	
}
getCategories():void{
	this.categorieService.getCategories().subscribe(
		(response:ICategorie[])=>{
			this.categorieList=response;
		}
	)
}
getMagazins():void{
	this.magazinService.getMagazins().subscribe(
		(response:IMagazin[])=>{
			this.magazinList=response;
		}
	)
}
getMarques():void{
	this.marqueService.getMarques().subscribe(
		(response:IMarque[])=>{
			this.marqueList=response;
		}
	)
}
 ProductByCat(cat ?: ICategorie):void{
	  const result: IProduit[] = [];
	  for(const prod of this.produitsList){
		if(prod.categorie.nomCategorie===cat?.nomCategorie){
			result.push(prod);
		}
	}
	this.produitsList = result;
      if (result.length === 0 ) {
    this.getProducts();
  }
} 
triParMagazin(magazin :IMagazin):void{
    const result: IProduit[] = [];
    this.stockService.getStockByMagazin(magazin.id!).subscribe(
	(response:IStock[])=>{
		this.stockList=response;
		for(const stock of this.stockList){
			if(stock.qte===0){
				stock.produit.status=false;
			}
	   result.push(stock.produit!);
      }
   this.produitsList=result;
	}
);
}
// eslint-disable-next-line @typescript-eslint/explicit-function-return-type

 ProductByMarque(marque ?: IMarque):void{
	  const result: IProduit[] = [];
	  for(const prod of this.produitsList){

		if(prod.marque.nomMarque===marque?.nomMarque){
			result.push(prod);
		}
	}
	this.produitsList = result;
      if (result.length === 0 ) {
    this.getProducts();
  }
} 
openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
 
public searchProduit(key: string): void {
  const results: IProduit[] = [];

  for (const prod of this.produitsList) {
    if (prod.nomProd&&prod.nomProd.toLowerCase().indexOf(key.toLowerCase()) !== -1
    || prod.categorie.nomCategorie.toLowerCase().indexOf(key.toLowerCase()) !== -1
    || prod.marque.nomMarque.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
	
      results.push(prod);
    }
  }
  this.produitsList = results;
  if (results.length === 0 || !key) {
    this.getProducts();
  }
}



}
