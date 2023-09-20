package org.example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.example.controller.ClienteController;
import org.example.controller.ContaBancariaController;
import org.example.controller.TransacaoController;
import org.example.model.ContaBancaria;
import org.example.model.Transacao;

public class SistemaBancarioMenu {

    ContaBancariaController contaController;
    TransacaoController transacaoController;
    ClienteController clienteController;

    public SistemaBancarioMenu() {
        this.contaController = new ContaBancariaController();
        this.transacaoController = new TransacaoController();
        this.clienteController = new ClienteController();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaBancarioMenu sistemaBancarioMenu = new SistemaBancarioMenu();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Criar Conta Bancária");
            System.out.println("2. Criar Cliente");
            System.out.println("3. Realizar Depósito");
            System.out.println("4. Realizar Saque");
            System.out.println("5. Verificar Saldo");
            System.out.println("6. Listar Transações");
            System.out.println("7. Listar Contas Por Cliente");
            System.out.println("8. Realizar transferência");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha;

            try {
                escolha = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                scanner.nextLine();
                continue;
            }

            switch (escolha) {
                case 1 -> sistemaBancarioMenu.criarContaBancaria(scanner);
                case 2 -> sistemaBancarioMenu.criarNovoCliente(scanner);
                case 3 -> sistemaBancarioMenu.realizarDeposito(scanner);
                case 4 -> sistemaBancarioMenu.realizarSaque(scanner);
                case 5 -> sistemaBancarioMenu.verificarSaldo(scanner);
                case 6 -> sistemaBancarioMenu.listarTransacoes(scanner);
                case 7 -> sistemaBancarioMenu.listarContasPorCliente(scanner);
                case 8 -> sistemaBancarioMenu.realizarTransferencia(scanner);
                case 9 -> {
                    System.out.println("Saindo do sistema.");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void criarContaBancaria(Scanner scanner) {
        String numeroConta = validarInputString(scanner, "Número da Conta: ");
        BigDecimal saldoInicial = validarInputBigDecimal(scanner, "Saldo Inicial: ");
        int clienteId = validarInputInt(scanner, "ID do Cliente: ");

        try {
            boolean clienteExiste = clienteController.verificarExistenciaCliente(clienteId);

            if (clienteExiste) {
                ContaBancaria conta = contaController.criarConta(numeroConta, saldoInicial, clienteId);
                if (conta != null) {
                    System.out.println("Conta criada com sucesso!");
                } else {
                    System.out.println("Falha ao criar a conta.");
                }
            } else {
                System.out.println("Cliente com ID " + clienteId + " não existe. A conta não foi criada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void realizarDeposito(Scanner scanner) {
        int contaId = validarInputInt(scanner, "ID da Conta: ");
        BigDecimal valor = validarInputBigDecimal(scanner, "Valor do Depósito: ");

        try {
            if (contaController.realizarDeposito(contaId, valor)) {
                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Falha ao realizar depósito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void realizarSaque(Scanner scanner) {

        int contaId = validarInputInt(scanner, "ID da Conta: ");
        BigDecimal valor = validarInputBigDecimal(scanner, "Valor do Saque: ");

        try {
            if (contaController.realizarSaque(contaId, valor)) {
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Falha ao realizar saque.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void verificarSaldo(Scanner scanner) {
        int contaId = validarInputInt(scanner, "ID da Conta: ");

        try {
            BigDecimal saldo = contaController.verificarSaldo(contaId);
            if (saldo != null) {
                System.out.println("Saldo atual: " + saldo);
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listarTransacoes(Scanner scanner) {

        int contaId = validarInputInt(scanner, "ID da Conta: ");

        try {
            List<Transacao> transacoes = transacaoController.listarTransacoesPorConta(contaId);
            System.out.println("Transações da conta:");
            for (Transacao transacao : transacoes) {
                System.out.println("Data: " + transacao.getData());
                System.out.println("Valor: " + transacao.getValor());
                System.out.println("Conta Origem ID: " + transacao.getContaOrigemId());
                System.out.println("Conta Destino ID: " + transacao.getContaDestinoId());
                System.out.println("-------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listarContasPorCliente(Scanner scanner) {
        int clienteId = validarInputInt(scanner, "ID do Cliente: ");

        try {
            boolean clienteExiste = clienteController.verificarExistenciaCliente(clienteId);

            if (clienteExiste) {
                List<ContaBancaria> contas = contaController.listarContasPorCliente(clienteId);

                if (contas.isEmpty()) {
                    System.out.println("O cliente com ID " + clienteId + " não possui contas bancárias.");
                } else {
                    System.out.println("Contas do cliente com ID " + clienteId + ":");
                    for (ContaBancaria conta : contas) {
                        System.out.println("Número da Conta: " + conta.getNumeroConta());
                        System.out.println("Saldo: " + conta.getSaldo());
                        System.out.println("-------------");
                    }
                }
            } else {
                System.out.println("Cliente com ID " + clienteId + " não existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void criarNovoCliente(Scanner scanner) {
        String nome = validarInputString(scanner, "Nome do Cliente: ");
        String cpf = validarInputString(scanner, "CPF do Cliente: ");
        String endereco = validarInputString(scanner, "Endereço do Cliente: ");

        try {
            boolean clienteCriado = clienteController.criarCliente(nome, cpf, endereco);
            if (clienteCriado) {
                System.out.println("Cliente criado com sucesso!");
            } else {
                System.out.println("Falha ao criar o cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void realizarTransferencia(Scanner scanner) {
        int contaOrigemId = validarInputInt(scanner, "ID da Conta de Origem: ");
        int contaDestinoId = validarInputInt(scanner, "ID da Conta de Destino: ");
        BigDecimal valor = validarInputBigDecimal(scanner, "Valor a ser transferido: ");

        try {
            boolean transferenciaSucesso = transacaoController.transferirDinheiro(contaOrigemId, contaDestinoId, valor);
            if (transferenciaSucesso) {
                System.out.println("Transferência realizada com sucesso!");
            } else {
                System.out.println("Falha ao realizar a transferência. Verifique o saldo da conta de origem.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }
    private String validarInputString(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        String input = scanner.nextLine();
        while (input.isEmpty()) {
            System.out.println("Entrada inválida. Por favor, insira um valor válido.");
            System.out.print(mensagem);
            input = scanner.nextLine();
        }
        return input;
    }

    private BigDecimal validarInputBigDecimal(Scanner scanner, String mensagem) {
        BigDecimal input = null;
        while (input == null) {
            System.out.print(mensagem);
            try {
                input = scanner.nextBigDecimal();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                scanner.nextLine();
            }
        }
        return input;
    }

    private int validarInputInt(Scanner scanner, String mensagem) {
        int input = -1;
        while (input == -1) {
            System.out.print(mensagem);
            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                scanner.nextLine();
            }
        }
        return input;
    }
}
