package inventory;

public abstract class Item {

    protected String type;
    protected String name;
    protected int count;
    protected double price;

    public Item(String type, String name, int count, double price) {
        this.type = type;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public double getValue() {
        return count * price;
    }

    public void reduceCount(int qty) {
        count -= qty;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }
}
