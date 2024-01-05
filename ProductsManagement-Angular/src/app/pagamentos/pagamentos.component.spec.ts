import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagamentoComponent } from './pagamentos.component';

describe('PagamentosComponent', () => {
  let component: PagamentoComponent;
  let fixture: ComponentFixture<PagamentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagamentoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PagamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
