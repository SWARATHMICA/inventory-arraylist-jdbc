package inventory;
import inventory.exception.InsufficientStockException;
import inventory.exception.ItemNotFoundException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of different items: ");
            int n = sc.nextInt();
            
            
            Inventory inventory = new Inventory();
            try (Connection conn = DatabaseConnection.connect();
                Statement stmt = conn.createStatement()) {

                String sql = "CREATE TABLE IF NOT EXISTS items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "quantity INTEGER," +
                        "type TEXT" +
                        ");";

                stmt.execute(sql);
                System.out.println("Table ready.");

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            for (int i = 0; i < n; i++) {
                
                System.out.println("\nEnter item type (nut / bolt): ");
                String type = sc.next().toLowerCase();
                
                System.out.print("Enter item name: ");
                String name = sc.next();
                
                System.out.print("Enter quantity: ");
                int count = sc.nextInt();
                
                System.out.print("Enter price per unit: ");
                double price = sc.nextDouble();
                
                Item item;
                
                switch (type) {
                    
                    case "nut" -> item = new Nut(type,name, count, price);
                    
                    case "bolt" -> item = new Bolt(type,name, count, price);
                    
                    default -> {
                        System.out.println("Invalid item type!");
                        i--;
                        continue;
                    }
                }
                
                inventory.addItem(item);
                inventory.addItemToDatabase(name, count, type);
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
            System.out.println("Remaining stock:");
            inventory.showAllItems();

        }
    }
}
