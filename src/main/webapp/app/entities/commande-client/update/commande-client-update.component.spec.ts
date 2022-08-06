import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommandeClientService } from '../service/commande-client.service';
import { ICommandeClient, CommandeClient } from '../commande-client.model';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

import { CommandeClientUpdateComponent } from './commande-client-update.component';

describe('CommandeClient Management Update Component', () => {
  let comp: CommandeClientUpdateComponent;
  let fixture: ComponentFixture<CommandeClientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commandeClientService: CommandeClientService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommandeClientUpdateComponent],
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
      .overrideTemplate(CommandeClientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandeClientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commandeClientService = TestBed.inject(CommandeClientService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const commandeClient: ICommandeClient = { id: 456 };
      const client: IClient = { id: 72732 };
      commandeClient.client = client;

      const clientCollection: IClient[] = [{ id: 77423 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commandeClient });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(clientCollection, ...additionalClients);
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commandeClient: ICommandeClient = { id: 456 };
      const client: IClient = { id: 87482 };
      commandeClient.client = client;

      activatedRoute.data = of({ commandeClient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(commandeClient));
      expect(comp.clientsSharedCollection).toContain(client);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeClient>>();
      const commandeClient = { id: 123 };
      jest.spyOn(commandeClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandeClient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(commandeClientService.update).toHaveBeenCalledWith(commandeClient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeClient>>();
      const commandeClient = new CommandeClient();
      jest.spyOn(commandeClientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commandeClient }));
      saveSubject.complete();

      // THEN
      expect(commandeClientService.create).toHaveBeenCalledWith(commandeClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CommandeClient>>();
      const commandeClient = { id: 123 };
      jest.spyOn(commandeClientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commandeClient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commandeClientService.update).toHaveBeenCalledWith(commandeClient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClientById', () => {
      it('Should return tracked Client primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
