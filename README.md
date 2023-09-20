# SistemaBancario

Este é um sistema bancário simples em Java que permite a criação de clientes, contas bancárias, realização de depósitos, saques, transferências e consulta de saldo. O sistema utiliza um banco de dados PostgreSQL para armazenar as informações dos clientes, contas bancárias e transações.

## Configuração do Banco de Dados

Certifique-se de configurar corretamente o banco de dados PostgreSQL com as informações de URL, Usuário e Senha.

Lembre-se de ajustar essas configurações no arquivo `DatabaseConnection.java`.

## Pacotes

O código está organizado em vários pacotes para facilitar a modularidade e a manutenção:

- `controller`: Contém as classes controladoras que lidam com a lógica de negócios.
- `dao`: Contém as classes de acesso aos dados (DAO) que interagem com o banco de dados.
- `model`: Contém as classes de modelo para representar clientes, contas bancárias e transações.
- `org.example`: Contém classes auxiliares e exceções personalizadas.

## Instruções de Uso

Para usar o sistema, siga as instruções apresentadas no menu principal:

1. Criar Conta Bancária: Crie uma nova conta bancária associada a um cliente existente.
2. Criar Cliente: Cadastre um novo cliente no sistema.
3. Realizar Depósito: Faça um depósito em uma conta bancária existente.
4. Realizar Saque: Realize um saque de uma conta bancária existente.
5. Verificar Saldo: Consulte o saldo de uma conta bancária.
6. Listar Transações: Exiba as transações de uma conta bancária.
7. Listar Contas Por Cliente: Liste todas as contas de um cliente.
8. Realizar Transferência: Transfira dinheiro entre contas bancárias.
9. Sair: Encerre o sistema.

Certifique-se de que o PostgreSQL esteja em execução e que o banco de dados esteja configurado corretamente antes de usar o sistema.

## Observações

Este é um sistema bancário simples, e não leva em consideração todos os aspectos de segurança e validações necessárias para um sistema real. É recomendável que você adicione mais validações e aprimore a segurança ao usar esse código em um ambiente de produção. Se sinta livre para implementar outras operações CRUD conforme necessário.

Aproveite o uso do Sistema Bancário!
