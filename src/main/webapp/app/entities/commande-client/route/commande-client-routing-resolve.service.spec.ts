import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICommandeClient, CommandeClient } from '../commande-client.model';
import { CommandeClientService } from '../service/commande-client.service';

import { CommandeClientRoutingResolveService } from './commande-client-routing-resolve.service';

describe('CommandeClient routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CommandeClientRoutingResolveService;
  let service: CommandeClientService;
  let resultCommandeClient: ICommandeClient | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(CommandeClientRoutingResolveService);
    service = TestBed.inject(CommandeClientService);
    resultCommandeClient = undefined;
  });

  describe('resolve', () => {
    it('should return ICommandeClient returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCommandeClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCommandeClient).toEqual({ id: 123 });
    });

    it('should return new ICommandeClient if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCommandeClient = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCommandeClient).toEqual(new CommandeClient());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CommandeClient })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCommandeClient = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCommandeClient).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
