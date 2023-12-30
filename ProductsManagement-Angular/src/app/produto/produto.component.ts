// produto.component.ts

import { Component, OnInit } from '@angular/core';
import { ProdutoService } from './produto.service';
import { Produto } from './produto';

@Component({
  selector: 'app-produto',
  templateUrl: './produto.component.html',
  styleUrls: ['./produto.component.css'],
})
export class ProdutoComponent implements OnInit {
  produtos: Produto[] = [];
  novoProduto: Produto = { id: 0, nome: '', preco: 0, categoriaId: 0, fornecedorId: 0 };
  modoEdicao: boolean = false;

  constructor(private produtoService: ProdutoService) {}

  ngOnInit(): void {
    this.carregarProdutos();
  }

  carregarProdutos(): void {
    this.produtoService.getAllProdutos().subscribe(
      (data) => {
        this.produtos = data;
      },
      (error) => {
        console.error('Erro ao obter produtos:', error);
      }
    );
  }

  salvarProduto(): void {
    if (this.modoEdicao) {
      this.atualizarProduto(this.novoProduto.id!);
    } else {
      this.produtoService
        .saveProduto(this.novoProduto, this.novoProduto.categoriaId!, this.novoProduto.fornecedorId!)
        .subscribe(
          (data) => {
            console.log('Produto salvo com sucesso:', data);
            this.carregarProdutos();
          },
          (error) => {
            console.error('Erro ao salvar produto:', error);
          }
        );
    }
  }

  atualizarProduto(id: number): void {
    this.produtoService.updateProduto(id, this.novoProduto).subscribe(
      (data) => {
        console.log('Produto atualizado com sucesso:', data);
        this.carregarProdutos();
      },
      (error) => {
        console.error('Erro ao atualizar produto:', error);
      }
    );
  }

  excluirProduto(id: number): void {
    this.produtoService.deleteProduto(id).subscribe(
      () => {
        console.log('Produto excluído com sucesso.');
        this.carregarProdutos();
      },
      (error) => {
        console.error('Erro ao excluir produto:', error);
      }
    );
  }

  editarProduto(produto: Produto): void {
    this.modoEdicao = true;
    this.novoProduto = { ...produto };
  }
}
