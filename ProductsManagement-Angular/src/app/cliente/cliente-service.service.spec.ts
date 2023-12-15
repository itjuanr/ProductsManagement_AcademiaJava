import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ClienteServiceService, Cliente } from './cliente-service.service';

describe('ClienteServiceService', () => {
  let service: ClienteServiceService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClienteServiceService]
    });

    service = TestBed.inject(ClienteServiceService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all clientes', () => {
    const mockClientes: Cliente[] = [
      { id: 1, nome: 'Cliente 1', email: 'cliente1@example.com', cpf: '12345678901' },
      { id: 2, nome: 'Cliente 2', email: 'cliente2@example.com', cpf: '98765432101' }
    ];

    service.getAllClientes().subscribe((clientes) => {
      expect(clientes).toEqual(mockClientes);
    });

    const req = httpTestingController.expectOne('http://localhost:8080/api/clientes');
    expect(req.request.method).toEqual('GET');

    req.flush(mockClientes);
  });
});
