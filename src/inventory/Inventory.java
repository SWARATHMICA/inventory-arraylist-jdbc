package inventory;

import java.util.HashMap;
import java.util.Map;

import inventory.exception.ItemNotFoundException;
import inventory.exception.InsufficientStockException;

public class Inventory {

    private Map<String, Item> items = new HashMap<>();

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


}
