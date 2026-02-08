package inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        items.put(item.getName(), item);
    }

    public void requestItem(String name, int quantity) {
        Item item = items.get(name);
        if (item != null && item.getCount() >= quantity) {
            item.reduceCount(quantity);
        }
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

}
