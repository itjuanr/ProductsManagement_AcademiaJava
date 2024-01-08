# Gerenciamento de Pedidos
## Projeto Final - AcademiaJava UFN

## Descrição

O projeto Gerenciamento de Pedidos é uma aplicação web desenvolvida para facilitar o controle de fornecedores, pedidos, produtos, clientes, categorias e pagamentos. Através de uma interface intuitiva, os usuários podem realizar operações como adição, edição, visualização e exclusão de informações relacionadas a diversos aspectos do processo de gerenciamento.

## Funcionalidades

### Fornecedores

- **Adição de Fornecedores:** Cadastre novos fornecedores inserindo informações como nome e CNPJ.
- **Edição de Fornecedores:** Atualize os dados de fornecedores existentes.
- **Exclusão de Fornecedores:** Remova fornecedores do sistema.

### Pedidos

- **Criação de Pedidos:** Realize a inclusão de novos pedidos, associando cliente, produto e quantidade.
- **Visualização Detalhada:** Acesse detalhes de pedidos existentes, incluindo informações sobre cliente, produto, quantidade e preço.
- **Atualização de Pedidos:** Modifique informações como quantidade, cliente e produto em pedidos existentes.
- **Exclusão de Pedidos:** Remova pedidos do sistema.

### Produtos

- **Adição de Produtos:** Cadastre novos produtos informando nome, preço, categoria e fornecedor.
- **Edição de Produtos:** Atualize informações de produtos já registrados.
- **Exclusão de Produtos:** Remova produtos do sistema.

### Clientes

- **Adição de Clientes:** Registre informações dos clientes, como nome, endereço e detalhes de contato.
- **Edição de Clientes:** Atualize informações de clientes existentes.
- **Exclusão de Clientes:** Remova clientes do sistema.

### Pagamentos

- **Registro de Pagamentos:** Registre informações sobre pagamentos associados a pedidos, incluindo método de pagamento e status.
- **Atualização de Pagamentos:** Modifique informações como status e método de pagamento em pagamentos existentes.
- **Exclusão de Pagamentos:** Remova registros de pagamento do sistema.

### Categorias

- **Adição de Categorias:** Cadastre novas categorias para organizar seus produtos.
- **Edição de Categorias:** Atualize informações de categorias existentes.
- **Exclusão de Categorias:** Remova categorias do sistema.

## Arquitetura e Tecnologias Utilizadas

### Front-end

O front-end da aplicação é construído em Angular, um framework robusto baseado em TypeScript. Angular proporciona uma estrutura modular e componentizada para o desenvolvimento de interfaces de usuário responsivas e interativas.

- **Angular:** Um framework de desenvolvimento front-end mantido pelo Google, que utiliza TypeScript para criar interfaces de usuário eficientes e dinâmicas.

- **TypeScript:** Uma linguagem de programação superset do JavaScript, que adiciona tipagem estática opcional ao JavaScript.

- **HTML e CSS:** Linguagens padrão da web utilizadas para estruturar e estilizar o conteúdo das páginas.

### Back-end

O back-end é implementado em Java utilizando o framework Spring Boot. O Spring Boot simplifica o desenvolvimento de aplicativos Java, oferecendo uma configuração fácil e abordagem de desenvolvimento baseada em convenções.

- **Java:** Uma linguagem de programação amplamente utilizada que fornece portabilidade, segurança e desempenho.

- **Spring Boot:** Um framework que simplifica o desenvolvimento de aplicativos Java, oferecendo configuração automática e uma variedade de recursos prontos para uso.

- **Spring Security e JWT (JSON Web Tokens):** Utilizados para autenticação e autorização, garantindo a segurança da aplicação.

- **JPA (Java Persistence API):** Uma API do Java que facilita o mapeamento objeto-relacional para o armazenamento e recuperação de dados.

- **MySQL:** Um sistema de gerenciamento de banco de dados relacional que armazena e gerencia os dados da aplicação.

### Ferramentas Adicionais

- **XAMPP:** Um pacote que inclui Apache, MySQL, PHP e Perl, facilitando a configuração de um ambiente de desenvolvimento local.

- **npm (Node Package Manager):** Utilizado para gerenciar as dependências do projeto no front-end.
## Contribuição

Sua contribuição é bem-vinda! Siga as etapas abaixo para contribuir:

1. Faça um fork do projeto.
2. Crie uma branch para suas alterações (`git checkout -b feature/MinhaNovaFeature`).
3. Faça commit de suas alterações (`git commit -am 'Adicionando uma nova feature'`).
4. Faça push para a branch (`git push origin feature/MinhaNovaFeature`).
5. Abra um Pull Request.
