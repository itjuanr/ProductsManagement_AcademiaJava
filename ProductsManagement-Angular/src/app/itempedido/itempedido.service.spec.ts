import { TestBed } from '@angular/core/testing';

import { ItempedidoService } from './itempedido.service';

describe('ItempedidoService', () => {
  let service: ItempedidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItempedidoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
