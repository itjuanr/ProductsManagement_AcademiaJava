import { Component, OnInit } from '@angular/core';
import { CategoriaService } from './categoria.service';
import { Categoria } from './categoria';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.css'],
})
export class CategoriaComponent implements OnInit {
  categorias: Categoria[] = [];
  novaCategoria: Categoria = { id: 0, nome: '', descricao: '' };
  isNewCategoria: boolean = true;

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.loadCategorias();
  }

  loadCategorias(): void {
    this.categoriaService.getAllCategorias().subscribe((categorias) => {
      this.categorias = categorias;
    });
  }

  createOrUpdateCategoria(): void {
    if (this.isNewCategoria) {
      this.createCategoria();
    } else {
      this.updateCategoria();
    }
  }

  createCategoria(): void {
    this.categoriaService.addCategoria(this.novaCategoria).subscribe(() => {
      this.loadCategorias();
      this.resetForm();
    });
  }

  updateCategoria(): void {
    this.categoriaService.updateCategoria(this.novaCategoria.id, this.novaCategoria).subscribe(() => {
      this.loadCategorias();
      this.resetForm();
    });
  }

  editCategoria(categoria: Categoria): void {
    this.novaCategoria = { ...categoria };
    this.isNewCategoria = false;
  }

  resetForm(): void {
    this.novaCategoria = { id: 0, nome: '', descricao: '' };
    this.isNewCategoria = true;
  }

  deleteCategoria(id: number): void {
    this.categoriaService.deleteCategoria(id).subscribe(() => {
      this.loadCategorias();
    });
  }
}
