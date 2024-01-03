// dashboard.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css'], 
})
export class DashboardComponent {
    dashboardFeatures = [
        {
            title: 'Clientes',
            description: 'Gerencie informações de seus clientes.',
            link: '/cliente',
        },
        {
            title: 'Categorias',
            description: 'Explore e administre categorias de produtos.',
            link: '/categoria',
        },
        {
            title: 'Fornecedores',
            description: 'Mantenha dados atualizados de seus fornecedores.',
            link: '/fornecedor',
        },
        {
            title: 'Produtos',
            description: 'Adicione, edite e exclua produtos de seu estoque.',
            link: '/produto',
        },
        {
            title: 'Pedidos',
            description: 'Acompanhe e gerencie pedidos realizados.',
            link: '/pedido',
        },
    ];

    constructor(private authService: AuthService) {}

    logout(): void {
        this.authService.logout();
    }
}
