import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produit',
        data: { pageTitle: 'Produits' },
        loadChildren: () => import('./produit/produit.module').then(m => m.ProduitModule),
      },
    {
        path: 'categorie',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./categorie/categorie.module').then(m => m.CategorieModule),
      },
      {
        path: 'marque',
        data: { pageTitle: 'Marques' },
        loadChildren: () => import('./marque/marque.module').then(m => m.MarqueModule),
      },
      {
        path: 'magazin',
        data: { pageTitle: 'Magazins' },
        loadChildren: () => import('./magazin/magazin.module').then(m => m.MagazinModule),
      },
      {
        path: 'arrivage',
        data: { pageTitle: 'Arrivages' },
        loadChildren: () => import('./arrivage/arrivage.module').then(m => m.ArrivageModule),
      },
      {
        path: 'bti',
        data: { pageTitle: 'Btis' },
        loadChildren: () => import('./bti/bti.module').then(m => m.BtiModule),
      },
      {
        path: 'stock',
        data: { pageTitle: 'Stocks' },
        loadChildren: () => import('./stock/stock.module').then(m => m.StockModule),
      },
      {
        path: 'employe',
        data: { pageTitle: 'Employes' },
        loadChildren: () => import('./employe/employe.module').then(m => m.EmployeModule),
      },
      {
        path: 'fournisseur',
        data: { pageTitle: 'Fournisseurs' },
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.FournisseurModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'Clients' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'location',
        data: { pageTitle: 'Locations' },
        loadChildren: () => import('./location/location.module').then(m => m.LocationModule),
      },
      {
        path: 'commande-fournisseur',
        data: { pageTitle: 'CommandeFournisseurs' },
        loadChildren: () => import('./commande-fournisseur/commande-fournisseur.module').then(m => m.CommandeFournisseurModule),
      },
      {
        path: 'commande-client',
        data: { pageTitle: 'CommandeClients' },
        loadChildren: () => import('./commande-client/commande-client.module').then(m => m.CommandeClientModule),
      },
      {
        path: 'facture',
        data: { pageTitle: 'Factures' },
        loadChildren: () => import('./facture/facture.module').then(m => m.FactureModule),
      },
      {
        path: 'paiement',
        data: { pageTitle: 'Paiements' },
        loadChildren: () => import('./paiement/paiement.module').then(m => m.PaiementModule),
      },
      {
        path: 'bon-commande',
        data: { pageTitle: 'BonCommandes' },
        loadChildren: () => import('./bon-commande/bon-commande.module').then(m => m.BonCommandeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
