package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.Sale;
import com.unicesumar.entities.SaleProduct;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.SaleProductRepository;
import com.unicesumar.repository.SaleRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        ProductRepository listaDeProdutos = null;
        UserRepository listaDeUsuarios = null;
        SaleProductRepository saleProductRepository = null;
        SaleRepository saleRepository = null;

        String url = "jdbc:sqlite:database.sqlite";
        Connection conn = null;


        // Tentativa de conexão
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                listaDeProdutos = new ProductRepository(conn);
                listaDeUsuarios = new UserRepository(conn);
                saleRepository = new SaleRepository(conn);
                saleProductRepository = new SaleProductRepository(conn);

            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
       }



        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---MENU---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listas Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Fazer venda");
            System.out.println("6 - Sair");
            System.out.println("Escolha uma opção: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Cadastrar Produto");
                    listaDeProdutos.save(new Product("Teste", 10));
                    listaDeProdutos.save(new Product("Computador", 3000));
                    break;
                case 2:
                    System.out.println("Listar Produtos");
                    List<Product> products = listaDeProdutos.findAll();
                    products.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Cadastrar Usuário");
                    listaDeUsuarios.save(new User("Rafael Labegalini", "rafael@example", "1234"));
                    break;
                case 4:
                    System.out.println("Listar Usuários");
                    List<User> users = listaDeUsuarios.findAll();
                    users.forEach(System.out::println);
                    break;

                case 5:
                    scanner.nextLine();
                    System.out.print("Digite o email do usuário: ");
                    String email = scanner.nextLine();
                    Optional<User> user = listaDeUsuarios.findByEmail(email);

                    if (user.isEmpty()) {
                        System.out.println("Usuário não encontrado!");
                        return;
                    }

                    System.out.print("Digite os IDs dos produtos (separados por vírgula): ");
                    String ids = scanner.nextLine();
                    String[] idList = ids.split(",");
                    List<Product> produtos = new ArrayList<>();
                    AtomicReference<Double> total = new AtomicReference<>((double) 0);

                    for (String idStr : idList) {
                        UUID pid = UUID.fromString(idStr.trim());
                        Optional<Product> p = listaDeProdutos.findById(pid);
                        p.ifPresent(product -> {
                            produtos.add(product);
                            total.updateAndGet(v -> v + product.getPrice());
                            System.out.printf("- %s (R$ %.2f)\n", product.getName(), product.getPrice());
                        });
                    }

                    System.out.println("Escolha a forma de pagamento:");
                    System.out.println("1 - Cartão de Crédito\n2 - Boleto\n3 - PIX");
                    int opcaoPagamento = scanner.nextInt();

                    String tipoDePagamento = switch (opcaoPagamento) {
                        case 1 -> "CARTAO";
                        case 2 -> "BOLETO";
                        case 3 -> "PIX";

                        default -> {
                            System.out.println("Invalidado!!!!!");
                            yield null;
                        }
                    };


                    UUID saleId = UUID.randomUUID();

//                    Sale venda = new Sale(saleId, user.get().getUuid(), tipoDePagamento);
//                    saleRepository.save(venda);

//                    user.ifPresent(user1 -> {
//                        Sale venda = new Sale(saleId, user1.getUuid(), tipoDePagamento);
//                        saleRepository.save(venda);
//                    });

                    if(user.isPresent()){
                        Sale venda = new Sale(saleId, user.get().getUuid(), tipoDePagamento);
                        saleRepository.save(venda);
                    }


                    for (Product p : produtos) {
                        saleProductRepository.save(new SaleProduct(saleId, p.getUuid()));
                    }

                    System.out.println("\nResumo da venda:");
                    System.out.println("Cliente: " + user.get().toString());
                    for (Product p : produtos) {
                        System.out.println("- " + p.getName() + ": " + p.getPrice());
                    }
                    System.out.printf("Valor total: R$ %.2f\n", total.get());
                    System.out.println("Pagamento: " + tipoDePagamento);
                    System.out.println("Venda registrada com sucesso!");

                    break;

                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente");
                    ;
            }

        } while (option != 6);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
