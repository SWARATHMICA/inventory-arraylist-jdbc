package inventory;

import inventory.exception.InsufficientStockException;
import inventory.exception.ItemNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        items.put(item.getName(), item);
    }


    public void requestItem(String name, int quantity) {

        Item item = items.get(name);

        if (item == null) {
            throw new ItemNotFoundException(name);
        }

        if (item.getCount() < quantity) {
            throw new InsufficientStockException(
                    name, item.getCount(), quantity);
        }

        item.reduceCount(quantity);
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public double calculateNetWorth() {
        double total = 0;
        for (Item item : items.values()) {
            total += item.calculateValue();
        }
        return total;
    }
    public Item getMostExpensiveItem() {
        Item maxItem = null;
        double maxValue = 0;

        for (Item item : items.values()) {
            double value = item.calculateValue();
            if (value > maxValue) {
                maxValue = value;
                maxItem = item;
            }
        }
        return maxItem;
    }


    @SuppressWarnings("CallToPrintStackTrace")
    public void addItemToDatabase(String name, int quantity, String type) {

        String sql = "INSERT INTO items(name, quantity, type) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, type);

            pstmt.executeUpdate();
            System.out.println("Item saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("CallToPrintStackTrace")
    public void showAllItems() {

        String sql = "SELECT * FROM items";

        try (Connection conn = DatabaseConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("quantity") + " | " +
                        rs.getString("type")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
