package inventory;
import inventory.exception.InsufficientStockException;
import inventory.exception.ItemNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of different items: ");
        int n = sc.nextInt();


        Inventory inventory = new Inventory();

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter item type (pushnut / outernut / bolt): ");
            String type = sc.next().toLowerCase();

            System.out.print("Enter item name: ");
            String name = sc.next();

            System.out.print("Enter quantity: ");
            int count = sc.nextInt();

            System.out.print("Enter price per unit: ");
            double price = sc.nextDouble();

            Item item = null;

            switch (type) {
                case "pushnut":
                    item = new PushNut(name, count, price);
                    break;

                case "outernut":
                    item = new OuterNut(name, count, price);
                    break;

                case "bolt":
                    item = new Bolt(name, count, price);
                    break;

                default:
                    System.out.println("Invalid item type!");
                    i--; 
                    continue;
            }

            inventory.addItem(item);
        }


        System.out.print("\nEnter number of requests: ");
        int requests = sc.nextInt();

        for (int i = 0; i < requests; i++) {
            try {
                System.out.print("Enter item name to request: ");
                String name = sc.next();

                System.out.print("Enter quantity required: ");
                int qty = sc.nextInt();

                inventory.requestItem(name, qty);
            } catch (ItemNotFoundException | InsufficientStockException e) {
                System.out.println("Error: " + e.getMessage());
                i--; // 🔁 retry same request
            }
        }


        System.out.println("Net worth: " + inventory.calculateNetWorth());
        System.out.println("Most expensive item: " +
                inventory.getMostExpensiveItem().getName());
    }
}
