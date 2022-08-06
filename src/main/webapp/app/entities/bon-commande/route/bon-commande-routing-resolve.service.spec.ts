import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBonCommande, BonCommande } from '../bon-commande.model';
import { BonCommandeService } from '../service/bon-commande.service';

import { BonCommandeRoutingResolveService } from './bon-commande-routing-resolve.service';

describe('BonCommande routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BonCommandeRoutingResolveService;
  let service: BonCommandeService;
  let resultBonCommande: IBonCommande | undefined;

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
    routingResolveService = TestBed.inject(BonCommandeRoutingResolveService);
    service = TestBed.inject(BonCommandeService);
    resultBonCommande = undefined;
  });

  describe('resolve', () => {
    it('should return IBonCommande returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBonCommande = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBonCommande).toEqual({ id: 123 });
    });

    it('should return new IBonCommande if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBonCommande = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBonCommande).toEqual(new BonCommande());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BonCommande })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBonCommande = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBonCommande).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
