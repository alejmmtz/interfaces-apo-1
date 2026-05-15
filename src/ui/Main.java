package ui;

import java.util.ArrayList;
import java.util.Scanner;
import model.Warehouse;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("Bodega Icesi", 20);

        while (true) {
            showMenu(warehouse);
            System.out.print("Seleccione una opcion: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> rentLocal(warehouse);
                case 2 -> countProduct(warehouse);
                case 3 -> calculateInsurance(warehouse);
                case 4 -> addProduct(warehouse);
                case 5 -> removeProduct(warehouse);
                case 0 -> {
                    System.out.println("Hasta pronto.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }

            System.out.println();
        }
    }

    private static void showMenu(Warehouse warehouse) {
        System.out.println("=== " + warehouse.getName() + " ===");
        System.out.println("Locales disponibles: " + warehouse.getAvailableLocals());
        System.out.println("1. Alquilar un local");
        System.out.println("2. Contar productos en local industrial");
        System.out.println("3. Calcular valor asegurado");
        System.out.println("4. Agregar producto a local industrial");
        System.out.println("5. Remover producto de local industrial");
        System.out.println("0. Salir");
    }

    private static void rentLocal(Warehouse warehouse) {
        String userId = readLine("Ingrese la identificacion del usuario: ");
        System.out.print("Tipo de local (1. Industrial, 2. Persona natural, 3. Objetos especiales): ");
        int type = sc.nextInt();
        sc.nextLine();

        String message = switch (type) {
            case 1 -> warehouse.leasingLocal(userId, readProducts());
            case 2 -> warehouse.leasingLocal(userId, readYesNo("Desea asegurar la mercancia? (s/n): "));
            case 3 -> {
                System.out.print("Ingrese el valor total de la mercancia: ");
                double value = sc.nextDouble();
                sc.nextLine();
                yield warehouse.leasingLocal(userId, value);
            }
            default -> "Tipo de local invalido.";
        };
        System.out.println(message);
    }

    private static void countProduct(Warehouse warehouse) {
        String userId = readLine("Ingrese la identificacion del usuario: ");
        System.out.println(warehouse.countProduct(userId, readLine("Ingrese el nombre del producto: ")));
    }

    private static void calculateInsurance(Warehouse warehouse) {
        System.out.println(warehouse.insuranceValue(readLine("Ingrese la identificacion del usuario: ")));
    }

    private static void addProduct(Warehouse warehouse) {
        String userId = readLine("Ingrese la identificacion del usuario: ");
        System.out.println(warehouse.addProduct(userId, readLine("Ingrese el nombre del producto a agregar: ")));
    }

    private static void removeProduct(Warehouse warehouse) {
        String userId = readLine("Ingrese la identificacion del usuario: ");
        System.out.println(warehouse.removeProduct(userId, readLine("Ingrese el nombre del producto a remover: ")));
    }

    private static ArrayList<String> readProducts() {
        ArrayList<String> products = new ArrayList<>();
        String data = readLine("Ingrese los nombres de los productos separados por coma: ").trim();

        if (data.isEmpty())
            return products;

        for (String part : data.split(",")) {
            String product = part.trim();
            if (!product.isEmpty())
                products.add(product);
        }

        return products;
    }

    private static String readLine(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    private static boolean readYesNo(String message) {
        while (true) {
            String answer = readLine(message);
            if (answer.equalsIgnoreCase("s"))
                return true;
            else if (answer.equalsIgnoreCase("n"))
                return false;
            else
                System.out.println("Responda con s o n.");
        }
    }
}