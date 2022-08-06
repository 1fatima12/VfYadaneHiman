import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BonCommandeService } from '../service/bon-commande.service';
import { IBonCommande, BonCommande } from '../bon-commande.model';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { CommandeFournisseurService } from 'app/entities/commande-fournisseur/service/commande-fournisseur.service';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { CommandeClientService } from 'app/entities/commande-client/service/commande-client.service';
import { IProduit } from 'app/entities/produit/produit.model';
import { ProduitService } from 'app/entities/produit/service/produit.service';

import { BonCommandeUpdateComponent } from './bon-commande-update.component';

describe('BonCommande Management Update Component', () => {
  let comp: BonCommandeUpdateComponent;
  let fixture: ComponentFixture<BonCommandeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bonCommandeService: BonCommandeService;
  let commandeFournisseurService: CommandeFournisseurService;
  let commandeClientService: CommandeClientService;
  let produitService: ProduitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BonCommandeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BonCommandeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BonCommandeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bonCommandeService = TestBed.inject(BonCommandeService);
    commandeFournisseurService = TestBed.inject(CommandeFournisseurService);
    commandeClientService = TestBed.inject(CommandeClientService);
    produitService = TestBed.inject(ProduitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CommandeFournisseur query and add missing value', () => {
      const bonCommande: IBonCommande = { id: 456 };
      const commandeF: ICommandeFournisseur = { id: 66724 };
      bonCommande.commandeF = commandeF;

      const commandeFournisseurCollection: ICommandeFournisseur[] = [{ id: 14384 }];
      jest.spyOn(commandeFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: commandeFournisseurCollection })));
      const additionalCommandeFournisseurs = [commandeF];
      const expectedCollection: ICommandeFournisseur[] = [...additionalCommandeFournisseurs, ...commandeFournisseurCollection];
      jest.spyOn(commandeFournisseurService, 'addCommandeFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      expect(commandeFournisseurService.query).toHaveBeenCalled();
      expect(commandeFournisseurService.addCommandeFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        commandeFournisseurCollection,
        ...additionalCommandeFournisseurs
      );
      expect(comp.commandeFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CommandeClient query and add missing value', () => {
      const bonCommande: IBonCommande = { id: 456 };
      const commandeC: ICommandeClient = { id: 51324 };
      bonCommande.commandeC = commandeC;

      const commandeClientCollection: ICommandeClient[] = [{ id: 17404 }];
      jest.spyOn(commandeClientService, 'query').mockReturnValue(of(new HttpResponse({ body: commandeClientCollection })));
      const additionalCommandeClients = [commandeC];
      const expectedCollection: ICommandeClient[] = [...additionalCommandeClients, ...commandeClientCollection];
      jest.spyOn(commandeClientService, 'addCommandeClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      expect(commandeClientService.query).toHaveBeenCalled();
      expect(commandeClientService.addCommandeClientToCollectionIfMissing).toHaveBeenCalledWith(
        commandeClientCollection,
        ...additionalCommandeClients
      );
      expect(comp.commandeClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Produit query and add missing value', () => {
      const bonCommande: IBonCommande = { id: 456 };
      const produit: IProduit = { id: 51972 };
      bonCommande.produit = produit;

      const produitCollection: IProduit[] = [{ id: 64466 }];
      jest.spyOn(produitService, 'query').mockReturnValue(of(new HttpResponse({ body: produitCollection })));
      const additionalProduits = [produit];
      const expectedCollection: IProduit[] = [...additionalProduits, ...produitCollection];
      jest.spyOn(produitService, 'addProduitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      expect(produitService.query).toHaveBeenCalled();
      expect(produitService.addProduitToCollectionIfMissing).toHaveBeenCalledWith(produitCollection, ...additionalProduits);
      expect(comp.produitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bonCommande: IBonCommande = { id: 456 };
      const commandeF: ICommandeFournisseur = { id: 88896 };
      bonCommande.commandeF = commandeF;
      const commandeC: ICommandeClient = { id: 89594 };
      bonCommande.commandeC = commandeC;
      const produit: IProduit = { id: 59176 };
      bonCommande.produit = produit;

      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bonCommande));
      expect(comp.commandeFournisseursSharedCollection).toContain(commandeF);
      expect(comp.commandeClientsSharedCollection).toContain(commandeC);
      expect(comp.produitsSharedCollection).toContain(produit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BonCommande>>();
      const bonCommande = { id: 123 };
      jest.spyOn(bonCommandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonCommande }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bonCommandeService.update).toHaveBeenCalledWith(bonCommande);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BonCommande>>();
      const bonCommande = new BonCommande();
      jest.spyOn(bonCommandeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonCommande }));
      saveSubject.complete();

      // THEN
      expect(bonCommandeService.create).toHaveBeenCalledWith(bonCommande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BonCommande>>();
      const bonCommande = { id: 123 };
      jest.spyOn(bonCommandeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonCommande });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bonCommandeService.update).toHaveBeenCalledWith(bonCommande);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCommandeFournisseurById', () => {
      it('Should return tracked CommandeFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommandeFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCommandeClientById', () => {
      it('Should return tracked CommandeClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCommandeClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProduitById', () => {
      it('Should return tracked Produit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProduitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
