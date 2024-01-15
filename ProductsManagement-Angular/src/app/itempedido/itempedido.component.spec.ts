import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItempedidoComponent } from './itempedido.component';

describe('ItempedidoComponent', () => {
  let component: ItempedidoComponent;
  let fixture: ComponentFixture<ItempedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItempedidoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ItempedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
